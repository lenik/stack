package com.bee32.sem.inventory.web;

import java.io.IOException;
import java.util.List;

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
import com.bee32.sem.world.thing.ScaleItem;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;

public class MaterialAdminBean
        extends MaterialCategorySupportBean {

    private static final long serialVersionUID = 1L;

    MaterialPriceDto materialPrice = new MaterialPriceDto().create();
    ScaleItem scaleItem = new ScaleItem();
    MaterialPreferredLocationDto preferredLocation = new MaterialPreferredLocationDto().create();
    MaterialWarehouseOptionDto warehouseOption = new MaterialWarehouseOptionDto().create();
    UserFileDto userFile = new UserFileDto().create();

    public MaterialAdminBean() {
        super(Material.class, MaterialDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        Integer categoryId = categoryTree.getSelectedId();
        // if (categoryId != null)
        // if (categoryId == null) // select none if no category.
        // categoryId = -1;
        if (categoryId != null)
            elements.add(MaterialCriteria.categoryOf(categoryId));
    }

    public void addModelRestricion() {
        setSearchFragment("model-spec", "型号含有 " + searchPattern, //
                MaterialCriteria.modelSpecLike(searchPattern, true));
        searchPattern = null;
    }

    public void addCategoryRestricion() {
        setSearchFragment("category", "分类含有 " + searchPattern, //
                MaterialCriteria.categoryLike(searchPattern));
        searchPattern = null;
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
            ctx.data.access(MaterialPrice.class).saveOrUpdate(_price);
            ctx.data.access(Material.class).evict(material.unmarshal());
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
        if (!DTOs.isNull(material)) {
            material = reload(material);
            UnitConvDto unitConv = material.getUnitConv();
            if (DTOs.isNull(unitConv)) {
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
        material = reload(material);
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
            _unitConv.setScale(scaleItem.getUnit().unmarshal(), scaleItem.getScale());
            ctx.data.access(UnitConv.class).saveOrUpdate(_unitConv);

            if (!alreadyHaveUnitConv) {
                _m.setUnitConv(_unitConv);
                ctx.data.access(Material.class).saveOrUpdate(_m);
                ctx.data.access(Material.class).evict(_m);
            }
            ctx.data.access(UnitConv.class).evict(_unitConv);
            uiLogger.info("添加换算关系成功.");
        } catch (Exception e) {
            uiLogger.error("添加换算关系出错!", e);
        }
    }

    public void deleteUnitScale() {
        MaterialDto material = getOpenedObject();
        material = reload(material);
        try {
            Unit unit = scaleItem.getUnit().unmarshal();
            UnitConvDto unitConv = material.getUnitConv();
            UnitConv _unitConv = unitConv.unmarshal();
            _unitConv.getScaleMap().remove(unit);

            ctx.data.access(UnitConv.class).saveOrUpdate(_unitConv);

            ctx.data.access(UnitConv.class).evict(_unitConv);
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
            ctx.data.access(MaterialPreferredLocation.class).saveOrUpdate(_preferredLocation);
            ctx.data.access(Material.class).evict(_preferredLocation.getMaterial());
            uiLogger.info("保存物料推荐库位成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料推荐库位出错!", e);
        }
    }

    public void deletePreferredLocation() {
        try {
            MaterialPreferredLocation _preferredLocation = preferredLocation.unmarshal();
            _preferredLocation.getMaterial().getPreferredLocations().remove(_preferredLocation);

            ctx.data.access(MaterialPreferredLocation.class).delete(_preferredLocation);
            ctx.data.access(Material.class).evict(_preferredLocation.getMaterial());
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
            ctx.data.access(MaterialWarehouseOption.class).saveOrUpdate(_warehouseOption);
            ctx.data.access(Material.class).evict(_warehouseOption.getMaterial());
            uiLogger.info("保存物料${inventoryTerms.warehouse}选项成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料${inventoryTerms.warehouse}选项出错!", e);
        }
    }

    public void deleteWarehouseOption() {
        try {
            MaterialWarehouseOption _warehouseOption = warehouseOption.unmarshal();
            _warehouseOption.getMaterial().getOptions().remove(_warehouseOption);

            ctx.data.access(MaterialWarehouseOption.class).delete(_warehouseOption);
            ctx.data.access(Material.class).evict(_warehouseOption.getMaterial());
            uiLogger.info("删除物料${inventoryTerms.warehouse}选项成功.");
        } catch (Exception e) {
            uiLogger.error("删除物料${inventoryTerms.warehouse}选项出错!", e);
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
                    ctx.data.access(FileBlob.class).saveOrUpdate(fileBlob);

                    UserFile userFile = new UserFile();
                    userFile.setPath(fileName);
                    userFile.setFileBlob(fileBlob);
                    userFile.setLabel("未命名物料附件");
                    ctx.data.access(UserFile.class).save(userFile);

                    MaterialDto material = getOpenedObject();
                    Material _m = material.unmarshal();
                    _m.getAttachments().add(userFile);
                    ctx.data.access(Material.class).saveOrUpdate(_m);

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

    public void reloadUserFile() {
        userFile = reload(userFile);
    }

    public void updateUserFile() {
        UserFile file = userFile.unmarshal();
        try {
            ctx.data.access(UserFile.class).saveOrUpdate(file);
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
            ctx.data.access(UserFile.class).deleteById(userFile.getId());
            ctx.data.access(Material.class).saveOrUpdate(_m);
            uiLogger.info("删除物料附件:" + userFile.getName() + "成功.");
        } catch (Exception e) {
            uiLogger.error("删除物料附件失败!", e);
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<MaterialAttributeDto> attributesMBean = ListMBean.fromEL(this, //
            "openedObject.attributes", MaterialAttributeDto.class);

    public ListMBean<MaterialAttributeDto> getAttributesMBean() {
        return attributesMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean postValidate(List<?> dtos) {
        for (Object dto : dtos) {
            MaterialDto material = (MaterialDto) dto;
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
        for (MaterialDto material : uMap.<MaterialDto> dtos()) {
            onDeleteMaterial(material);
        }
    }

}
