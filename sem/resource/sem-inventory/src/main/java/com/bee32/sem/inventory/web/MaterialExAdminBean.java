package com.bee32.sem.inventory.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.ox1.tree.TreeEntityUtils;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.web.IncomingFile;
import com.bee32.sem.file.web.IncomingFileBlobAdapter;
import com.bee32.sem.inventory.dto.MaterialAttributeDto;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.MaterialPriceDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialAttribute;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.sandbox.ITreeNodeDecorator;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.thing.ScaleItem;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitDto;

public class MaterialExAdminBean
        extends SimpleEntityViewBean
        implements ITreeNodeDecorator {

    private static final long serialVersionUID = 1L;

    TreeNode virtualRootNode;
    Map<Integer, MaterialCategoryDto> materialCategoryIndex;
    TreeNode selectedMaterialCategoryNode;
    TreeNode choosedMaterialCategoryNode;

    MaterialPriceDto materialPrice = new MaterialPriceDto().create();
    ScaleItem scaleItem = new ScaleItem();
    MaterialAttributeDto materialAttr = new MaterialAttributeDto().create();
    MaterialPreferredLocationDto preferredLocation = new MaterialPreferredLocationDto().create();
    TreeNode selectedPreferredLocation;
    MaterialWarehouseOptionDto warehouseOption = new MaterialWarehouseOptionDto().create();
    UserFileDto userFile = new UserFileDto().create();

    public MaterialExAdminBean() {
        super(Material.class, MaterialDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        Integer categoryId = getSelectedMaterialCategoryId();
        // if (categoryId != null)
        if (categoryId == null) // select none if no category.
            categoryId = -1;
        elements.add(MaterialCriteria.categoryOf(categoryId));
    }

    public TreeNode getRoot() {
        loadTree();
        return virtualRootNode;
    }

    public TreeNode getSelectedMaterialCategoryNode() {
        return selectedMaterialCategoryNode;
    }

    public void setSelectedMaterialCategoryNode(TreeNode node) {
        this.selectedMaterialCategoryNode = node;
    }

    Integer getSelectedMaterialCategoryId() {
        if (selectedMaterialCategoryNode == null)
            return null;
        MaterialCategoryDto selectedCategory = (MaterialCategoryDto) selectedMaterialCategoryNode.getData();
        if (selectedCategory == null) // may be virtual-node?
            return null;
        return selectedCategory.getId();
    }

    synchronized void loadTree() {
        if (virtualRootNode == null) {
            List<MaterialCategory> _categories = serviceFor(MaterialCategory.class).list();
            List<MaterialCategoryDto> categories = DTOs.mrefList(MaterialCategoryDto.class, TreeEntityDto.PARENT,
                    _categories);
            materialCategoryIndex = DTOs.index(categories);
            Set<MaterialCategoryDto> roots = TreeEntityUtils.rebuildTree(categories, materialCategoryIndex);

            virtualRootNode = new DefaultTreeNode("categoryRoot", null);
            UIHelper.buildTree(this, roots, virtualRootNode);
        }
    }

    @Override
    public void decorate(TreeNode node) {
        MaterialCategoryDto category = (MaterialCategoryDto) node.getData();
        if (category != null) {
            Integer categoryId = category.getId();
            if (categoryId.equals(getSelectedMaterialCategoryId()))
                node.setSelected(true);
        }
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (MaterialDto material : uMap.<MaterialDto> dtos()) {
            if (material.getCategory() == null || material.getCategory().getId() == null) {
                uiLogger.error("物料所属分类不能为空!");
                return false;
            }

            if (material.getUnit() == null || material.getUnit().getId() == null
                    || material.getUnit().getId().isEmpty()) {
                uiLogger.error("主单位不能为空!");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (MaterialDto material : uMap.<MaterialDto> dtos()) {
            if (material.getId() == null) {
                Integer categoryId = material.getCategory().getId();
                MaterialCategoryDto category = materialCategoryIndex.get(categoryId);
                if (category != null)
                    category.setMaterialCount(category.getMaterialCount() + 1);
            }
        }
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        for (Material _m : uMap.<Material> entitySet()) {
            Integer categoryId = _m.getCategory().getId();
            MaterialCategoryDto category = materialCategoryIndex.get(categoryId);
            if (category != null)
                category.setMaterialCount(category.getMaterialCount() - 1);
        }
    }

    public List<SelectItem> getUnits() {
        List<Unit> units = serviceFor(Unit.class).list();
        List<UnitDto> unitDtos = DTOs.marshalList(UnitDto.class, units);
        return UIHelper.selectItemsFromDict(unitDtos);
    }

    public TreeNode getChoosedMaterialCategoryNode() {
        return choosedMaterialCategoryNode;
    }

    public void setChoosedMaterialCategoryNode(TreeNode node) {
        this.choosedMaterialCategoryNode = node;
    }

    public void chooseMaterialCategory() {
        MaterialDto material = getActiveObject();
        MaterialCategoryDto category = (MaterialCategoryDto) choosedMaterialCategoryNode.getData();
        material.setCategory(category);
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public List<MaterialPriceDto> getMaterialPrices() {
        MaterialDto material = getActiveObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.PRICES);
            return material.getPrices();
        }
        return null;
    }

    public MaterialPriceDto getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(MaterialPriceDto materialPrice) {
        this.materialPrice = materialPrice;
    }

    public void newMaterialPrice() {
        materialPrice = new MaterialPriceDto().create();
    }

    public void addMaterialPrice() {
        MaterialDto material = getActiveObject();
        try {
            materialPrice.setMaterial(material);
            MaterialPrice _price = materialPrice.unmarshal();
            serviceFor(MaterialPrice.class).saveOrUpdate(_price);
            serviceFor(Material.class).evict(material.unmarshal());
            uiLogger.info("保存物料价格成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料价格出错!", e);
        }
    }

    public ScaleItem getScaleItem() {
        return scaleItem;
    }

    public void setScaleItem(ScaleItem scaleItem) {
        this.scaleItem = scaleItem;
    }

    public List<ScaleItem> getScaleList() {
        MaterialDto material = getActiveObject();
        if (material != null && material.getId() != null) {
            UnitConvDto unitConv = material.getUnitConv();
            if (unitConv == null || unitConv.getId() == null) {
                return null;
            } else {
                unitConv = reload(unitConv, UnitConvDto.MAP);
                return unitConv.getItemList();
            }
        }
        return null;
    }

    public void newUnitScale() {
        scaleItem = new ScaleItem();
    }

    @Transactional
    public void addUnitScale() {
        MaterialDto material = getActiveObject();
        try {
            UnitConvDto unitConv = material.getUnitConv();
            UnitConv _unitConv = unitConv.unmarshal();
            Material _m = material.unmarshal();

            boolean alreadyHaveUnitConv = true;
            if (_unitConv == null) {
                _unitConv = new UnitConv();
                alreadyHaveUnitConv = false;
            }

            _unitConv.setUnit(_m.getUnit());
            _unitConv.setLabel(_m.getLabel());
            _unitConv.getScaleMap().put(scaleItem.getUnit().unmarshal(), scaleItem.getScale());
            serviceFor(UnitConv.class).saveOrUpdate(_unitConv);

            if (!alreadyHaveUnitConv) {
                _m.setUnitConv(_unitConv);
                serviceFor(Material.class).saveOrUpdate(_m);
                serviceFor(Material.class).evict(_m);
            }
            serviceFor(UnitConv.class).evict(_unitConv);
            uiLogger.info("添加换算关系成功.");
        } catch (Exception e) {
            uiLogger.error("添加换算关系出错!", e);
        }
    }

    public void deleteUnitScale() {
        MaterialDto material = getActiveObject();
        try {
            Unit unit = scaleItem.getUnit().unmarshal();
            UnitConvDto unitConv = material.getUnitConv();
            UnitConv _unitConv = unitConv.unmarshal();
            _unitConv.getScaleMap().remove(unit);

            serviceFor(UnitConv.class).saveOrUpdate(_unitConv);

            serviceFor(UnitConv.class).evict(_unitConv);
            uiLogger.info("删除换算关系成功.");
        } catch (Exception e) {
            uiLogger.error("删除换算关系出错!", e);
        }
    }

    public MaterialAttributeDto getMaterialAttr() {
        return materialAttr;
    }

    public void setMaterialAttr(MaterialAttributeDto materialAttr) {
        this.materialAttr = materialAttr;
    }

    public void newMaterialAttribute() {
        materialAttr = new MaterialAttributeDto().create();
    }

    public List<MaterialAttributeDto> getMaterialAttributes() {
        MaterialDto material = getActiveObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.ATTRBUTES);
            return material.getAttributes();
        }
        return null;
    }

    public void addMaterialAttribute() {
        MaterialDto material = getActiveObject();
        try {
            materialAttr.setMaterial(material);
            MaterialAttribute _attr = materialAttr.unmarshal();
            serviceFor(MaterialAttribute.class).saveOrUpdate(_attr);
            serviceFor(Material.class).evict(_attr.getMaterial());
            uiLogger.info("保存物料属性成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料属性出错!", e);
        }
    }

    public void deleteMaterialAttribute() {
        try {
            MaterialAttribute _attr = materialAttr.unmarshal();
            _attr.getMaterial().getAttributes().remove(_attr);

            serviceFor(MaterialAttribute.class).delete(_attr);
            serviceFor(Material.class).evict(_attr.getMaterial());
            uiLogger.info("删除物料属性成功.");
        } catch (Exception e) {
            uiLogger.error("删除物料属性出错!", e);
        }
    }

    public MaterialPreferredLocationDto getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(MaterialPreferredLocationDto preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public TreeNode getSelectedPreferredLocation() {
        return selectedPreferredLocation;
    }

    public void setSelectedPreferredLocation(TreeNode selectedPreferredLocation) {
        this.selectedPreferredLocation = selectedPreferredLocation;
    }

    public void newPreferredLocation() {
        preferredLocation = new MaterialPreferredLocationDto().create();
    }

    public List<MaterialPreferredLocationDto> getPreferredLocations() {
        MaterialDto material = getActiveObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.PREFERRED_LOCATIONS);
            return material.getPreferredLocations();
        }
        return null;
    }

    public void choosePreferredLocation() {
        StockLocationDto location = (StockLocationDto) selectedPreferredLocation.getData();
        if (location != null) {
            preferredLocation.setLocation(location);
        }
    }

    public void addPreferredLocation() {
        MaterialDto material = getActiveObject();
        try {
            preferredLocation.setMaterial(material);
            MaterialPreferredLocation _preferredLocation = preferredLocation.unmarshal();
            serviceFor(MaterialPreferredLocation.class).saveOrUpdate(_preferredLocation);
            serviceFor(Material.class).evict(_preferredLocation.getMaterial());
            uiLogger.info("保存物料推荐库位成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料推荐库位出错!", e);
        }
    }

    public void deletePreferredLocation() {
        try {
            MaterialPreferredLocation _preferredLocation = preferredLocation.unmarshal();
            _preferredLocation.getMaterial().getPreferredLocations().remove(_preferredLocation);

            serviceFor(MaterialPreferredLocation.class).delete(_preferredLocation);
            serviceFor(Material.class).evict(_preferredLocation.getMaterial());
            uiLogger.info("删除物料推荐库位成功.");
        } catch (Exception e) {
            uiLogger.error("删除物料推荐库位出错!", e);
        }
    }

    public MaterialWarehouseOptionDto getWarehouseOption() {
        return warehouseOption;
    }

    public void setWarehouseOption(MaterialWarehouseOptionDto warehouseOption) {
        this.warehouseOption = warehouseOption;
    }

    public void newWarehouseOption() {
        warehouseOption = new MaterialWarehouseOptionDto().create();
    }

    public List<MaterialWarehouseOptionDto> getWarehouseOptions() {
        MaterialDto material = getActiveObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.OPTIONS);
            return material.getOptions();
        }
        return null;
    }

    public void addWarehouseOption() {
        MaterialDto material = getActiveObject();
        try {
            warehouseOption.setMaterial(material);
            MaterialWarehouseOption _warehouseOption = warehouseOption.unmarshal();
            serviceFor(MaterialWarehouseOption.class).saveOrUpdate(_warehouseOption);
            serviceFor(Material.class).evict(_warehouseOption.getMaterial());
            uiLogger.info("保存物料仓库选项成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料仓库选项出错!", e);
        }
    }

    public void deleteWarehouseOption() {
        try {
            MaterialWarehouseOption _warehouseOption = warehouseOption.unmarshal();
            _warehouseOption.getMaterial().getOptions().remove(_warehouseOption);

            serviceFor(MaterialWarehouseOption.class).delete(_warehouseOption);
            serviceFor(Material.class).evict(_warehouseOption.getMaterial());
            uiLogger.info("删除物料仓库选项成功.");
        } catch (Exception e) {
            uiLogger.error("删除物料仓库选项出错!", e);
        }
    }

    public UserFileDto getUserFile() {
        return userFile;
    }

    public void setUserFile(UserFileDto userFile) {
        this.userFile = userFile;
    }

    public List<UserFileDto> getUserFiles() {
        MaterialDto material = getActiveObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.ATTACHMENTS);
            return material.getAttachments();
        }
        return null;
    }

    public Object getAddUserFileListener() {
        return new IncomingFileBlobAdapter() {
            @Override
            protected void process(FileBlob fileBlob, IncomingFile incomingFile)
                    throws IOException {
                String fileName = incomingFile.getFileName();
                try {
                    asFor(FileBlob.class).saveOrUpdate(fileBlob);

                    UserFile userFile = new UserFile();
                    userFile.setPath(fileName);
                    userFile.setFileBlob(fileBlob);
                    userFile.setLabel("未命名物料附件");
                    serviceFor(UserFile.class).save(userFile);

                    MaterialDto material = getActiveObject();
                    Material _m = material.unmarshal();
                    _m.getAttachments().add(userFile);
                    serviceFor(Material.class).saveOrUpdate(_m);

                    uiLogger.info("物料附件上传成功." + fileName);
                } catch (Exception e) {
                    uiLogger.error("远程文件保存失败:" + fileName, e);
                    return;
                }
            }

            @Override
            protected void reportError(String message, Exception exception) {
                uiLogger.error(message, exception);
            }
        };
    }

    public void updateUserFile() {
        UserFile file = userFile.unmarshal();
        try {
            serviceFor(UserFile.class).saveOrUpdate(file);
            uiLogger.info("编辑附件" + userFile.getName() + "成功.");
        } catch (Exception e) {
            uiLogger.error("编辑附件失败!", e);
        }
    }

    @Transactional
    public void deleteUserFile() {
        MaterialDto material = getActiveObject();
        try {
            UserFile _f = userFile.unmarshal();
            Material _m = material.unmarshal();
            _m.getAttachments().remove(_f);
            serviceFor(UserFile.class).deleteById(userFile.getId());
            serviceFor(Material.class).saveOrUpdate(_m);
            uiLogger.info("删除物料附件:" + userFile.getName() + "成功.");
        } catch (Exception e) {
            uiLogger.error("删除物料附件失败!", e);
        }
    }

}
