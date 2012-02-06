package com.bee32.sem.inventory.web;

import java.io.IOException;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.web.IncomingFile;
import com.bee32.sem.file.web.IncomingFileBlobAdapter;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialAttributeDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.MaterialPriceDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.ScaleItem;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitDto;

public class MaterialExAdminBean
        extends MaterialCategorySupportBean {

    private static final long serialVersionUID = 1L;

    MaterialPriceDto materialPrice = new MaterialPriceDto().create();
    ScaleItem scaleItem = new ScaleItem();
    MaterialPreferredLocationDto preferredLocation = new MaterialPreferredLocationDto().create();
    MaterialWarehouseOptionDto warehouseOption = new MaterialWarehouseOptionDto().create();
    UserFileDto userFile = new UserFileDto().create();

    public MaterialExAdminBean() {
        super(Material.class, MaterialDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        super.composeBaseCriteriaElements(elements);
        Integer categoryId = categoryTree.getSelectedId();
        // if (categoryId != null)
        if (categoryId == null) // select none if no category.
            categoryId = -1;
        elements.add(MaterialCriteria.categoryOf(categoryId));
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
            if (material.isNewCreated())
                onCreateMaterial(material);
        }
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        for (Material _m : uMap.<Material> entitySet()) {
            MaterialDto material = uMap.getSourceDto(_m);
            onDeleteMaterial(material);
        }
    }

    public List<SelectItem> getUnits() {
        List<Unit> units = serviceFor(Unit.class).list();
        List<UnitDto> unitDtos = DTOs.marshalList(UnitDto.class, units);
        return UIHelper.selectItemsFromDict(unitDtos);
    }

    public List<MaterialPriceDto> getMaterialPrices() {
        MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
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

    public MaterialPreferredLocationDto getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(MaterialPreferredLocationDto preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public void newPreferredLocation() {
        preferredLocation = new MaterialPreferredLocationDto().create();
    }

    public List<MaterialPreferredLocationDto> getPreferredLocations() {
        MaterialDto material = getOpenedObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.PREFERRED_LOCATIONS);
            return material.getPreferredLocations();
        }
        return null;
    }

    public void addPreferredLocation() {
        MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
        if (material != null && material.getId() != null) {
            material = reload(material, MaterialDto.OPTIONS);
            return material.getOptions();
        }
        return null;
    }

    public void addWarehouseOption() {
        MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
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

                    MaterialDto material = getOpenedObject();
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
        MaterialDto material = getOpenedObject();
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

    ListMBean<MaterialAttributeDto> attributesMBean = ListMBean.fromEL(this, "openedObject.attributes",
            MaterialAttributeDto.class);

    public ListMBean<MaterialAttributeDto> getAttributesMBean() {
        return attributesMBean;
    }

}
