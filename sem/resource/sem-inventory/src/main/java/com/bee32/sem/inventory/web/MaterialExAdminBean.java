package com.bee32.sem.inventory.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
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
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.thing.ScaleItem;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitDto;

public class MaterialExAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    TreeNode root;
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
        reloadTree();
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
        return root;
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

    void reloadTree() {
        root = new DefaultTreeNode("categoryRoot", null);

        List<MaterialCategory> topCategories = serviceFor(MaterialCategory.class).list(//
                TreeCriteria.root());
        List<MaterialCategoryDto> topCategoryDtos = DTOs.mrefList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, topCategories);

        for (MaterialCategoryDto materialCategoryDto : topCategoryDtos)
            _loadTree(materialCategoryDto, root);
    }

    private void _loadTree(MaterialCategoryDto materialCategoryDto, TreeNode parentTreeNode) {
        TreeNode treeNode = new DefaultTreeNode(materialCategoryDto, parentTreeNode);

        if (materialCategoryDto.getId().equals(getSelectedMaterialCategoryId()))
            treeNode.setSelected(true);

        for (MaterialCategoryDto child : materialCategoryDto.getChildren())
            _loadTree(child, treeNode);
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
        for (Material _m : uMap.<Material> entitySet()) {
            MaterialDto material = uMap.getSourceDto(_m);
            if (material.getId() == null) {
                MaterialCategory _category = _m.getCategory();
                _category.addMaterial(_m);
                _category.setMaterialCount(_category.getMaterialCount() + 1);
            }
        }
        reloadTree();
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (Material _m : uMap.<Material> entitySet()) {
            MaterialCategory _category = _m.getCategory();
            _category.removeMaterial(_m);
            _category.setMaterialCount(_category.getMaterialCount() - 1);
        }
        return true;
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        reloadTree();
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

    @Transactional
    public void handleFileUpload(FileUploadEvent event) {
        String fileName = event.getFile().getFileName();
        UploadedFile upFile = event.getFile();

        File tempFile;
        try {
            tempFile = TempFile.createTempFile();
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(upFile.getContents());
            fileOutputStream.close();
        } catch (Exception e) {
            uiLogger.error("传送失败:" + fileName, e);
            return;
        }

        try {
            UserFile userFile = new UserFile();
            userFile.setPath(upFile.getFileName());

            FileBlob fileBlob = FileBlob.commit(tempFile, true);

            userFile.setFileBlob(fileBlob);
            userFile.setLabel("未命名物料附件");

            serviceFor(FileBlob.class).saveOrUpdate(fileBlob);
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
