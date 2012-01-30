package com.bee32.sem.bom.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Not;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.dto.PartItemDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.entity.PartItem;
import com.bee32.sem.bom.service.MaterialPriceNotFoundException;
import com.bee32.sem.bom.service.PartService;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.misc.DummyDto;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(Part.class)
public class BomAdminBean
        extends BomAdminVdx {

    private static final long serialVersionUID = 1L;

    private PartDto part = DummyDto.create(PartDto.class);
    private PartItemDto item = DummyDto.create(PartItemDto.class);

    private PartDto selectedPart;
    private PartItemDto selectedItem;

    private LazyDataModel<PartDto> parts;

    private boolean partOrComponent;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private String partPattern;
    private List<PartDto> findedParts;
    private PartDto selectedFindedPart;

    private BigDecimal price;

    private MaterialCategoryTreeModel materialCategoryTree;

    public BomAdminBean() {
        initMaterialCategoryTree();
    }

    public void initMaterialCategoryTree() {
        List<MaterialCategory> rootCategories = //
        serviceFor(MaterialCategory.class).list( //
                TreeCriteria.root(), //
                Or.of( //
                new Equals("_classification", Classification.PRODUCT.getValue()), //
                        new Equals("_classification", Classification.SEMI.getValue()) //
                ) //
                );
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.mrefList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);

        materialCategoryTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialCategoryTree.addListener(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();

                EntityDataModelOptions<Part, PartDto> options = new EntityDataModelOptions<Part, PartDto>(//
                        Part.class, PartDto.class, 0, //
                        BomCriteria.listPartByCategory(materialCategoryDto.getId()));
                parts = UIHelper.<Part, PartDto> buildLazyDataModel(options);

                refreshPartCount(materialCategoryDto.getId());

            }
        });
    }

    void refreshPartCount(int materialCategoryId) {
        int count = serviceFor(Part.class).count(BomCriteria.listPartByCategory(materialCategoryId));
        parts.setRowCount(count);
    }

    public PartItemDto getItem() {
        return item;
    }

    public void setPart(PartItemDto item) {
        this.item = item;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        this.part = part;
    }

    public PartItemDto getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(PartItemDto selectedItem) {
        this.selectedItem = selectedItem;
    }

    public PartDto getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(PartDto selectedPart) {
        this.selectedPart = selectedPart;
    }

    public LazyDataModel<PartDto> getParts() {
        return parts;
    }

    public void setParts(LazyDataModel<PartDto> parts) {
        this.parts = parts;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public String getPartPattern() {
        return partPattern;
    }

    public void setPartPattern(String partPattern) {
        this.partPattern = partPattern;
    }

    public List<PartDto> getFindedParts() {
        return findedParts;
    }

    public void setFindedParts(List<PartDto> findedParts) {
        this.findedParts = findedParts;
    }

    public PartDto getSelectedFindedPart() {
        return selectedFindedPart;
    }

    public void setSelectedFindedPart(PartDto selectedFindedPart) {
        this.selectedFindedPart = selectedFindedPart;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isPartSelected() {
        if (selectedPart != null)
            return true;
        return false;
    }

    public MaterialCategoryTreeModel getMaterialCategoryTree() {
        return materialCategoryTree;
    }

    public void newPart() {
        setTabIndex(TAB_FORM);
        editable = true;
        part = new PartDto().create();
    }

    public void deletePart() {
        if (selectedPart == null) {
            uiLogger.info("请以单击选择需要删除的产品!");
            return;
        }

        try {
            if (selectedPart.getXrefCount() != 0) {
                uiLogger.error("此产品已经包含组件，必须先删除相应组件，才能删除本产品!");
                return;
            }

            serviceFor(Part.class).deleteById(selectedPart.getId());

            uiLogger.info("删除产品成功");
        } catch (Exception e) {
            uiLogger.error("删除产品失败;", e);
        }
    }

    @Transactional
    public void savePart() {
        try {
            part.setLastModified(new Date());

            Part _part = part.unmarshal(this);
            serviceFor(Part.class).saveOrUpdate(_part);
            getBean(PartService.class).changePartItemFromMaterialToPart(_part);

            if (materialCategoryTree.getSelectedNode() != null) {
                MaterialCategoryDto c = (MaterialCategoryDto) materialCategoryTree.getSelectedNode().getData();
                refreshPartCount(c.getId());
            }

            setTabIndex(TAB_INDEX);
            editable = false;
            uiLogger.info("保存产品成功");
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            uiLogger.error("保存产品失败", e);
        }
    }

    public void listParts() {
        setTabIndex(TAB_INDEX);
        editable = false;
        part = DummyDto.create(PartDto.class);
    }

    public void showPartDetail() {
        if (selectedPart == null) {
            uiLogger.info("请以单击选择需要查看详细内容的产品");
            return;
        }

        setTabIndex(TAB_FORM);
        part = reload(selectedPart, PartDto.CHILDREN);
    }

    public void refreshPart() {
        setTabIndex(TAB_INDEX);
        selectedPart = null;
        MaterialCategoryDto c = (MaterialCategoryDto) materialCategoryTree.getSelectedNode().getData();
        refreshPartCount(c.getId());
    }

    public void newItem() {
        if (part == null || part.getId() == null) {
            uiLogger.error("请选择相应的产品!");
            return;
        }

        currTabComp = 1;
        editableComp = true;

        item = new PartItemDto().create();
        item.setValid(true);
    }

    public void deleteItem() {
        if (selectedItem == null) {
            uiLogger.error("请以单击选择需要删除的BOM组件!");
            return;
        }

        try {
            serviceFor(PartItem.class).deleteById(selectedItem.getId());
            part.removeChild(selectedItem);

            uiLogger.info("删除BOM组件成功");
        } catch (Exception e) {
            uiLogger.error("删除BOM组件失败", e);
        }
    }

    public void saveItem() {
        if (part == null || part.getId() == null) {
            uiLogger.error("请选择相应的产品!");
            return;
        }

        try {
            PartDto parentRef = new PartDto().ref(part);
            item.setParent(parentRef);

            PartItem _item = item.unmarshal(this);
            serviceFor(PartItem.class).saveOrUpdate(_item);

            part = reload(part);

            currTabComp = 0;
            editableComp = false;
            uiLogger.info("保存BOM组件成功");
        } catch (Exception e) {
            uiLogger.error("保存BOM组件失败", e);
        }
    }

    public void listItems() {
        currTabComp = 0;
        editableComp = false;
        item = DummyDto.create(PartItemDto.class);
    }

    public void showItemDetail() {
        if (selectedItem == null) {
            uiLogger.error("请以单击选择需要查看详细内容的BOM组件!");
            return;
        }

        currTabComp = 1;
        item = selectedItem;
    }

    public void refreshItem() {
        currTabComp = 0;
        selectedItem = null;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    public List<PartItemDto> getItems() {
        if (part != null && part.getId() != null)
            return part.getChildren();
        else
            return new ArrayList<PartItemDto>();
    }

    /**
     * 设置当前是为bom产品选择物料
     */
    public void setMaterialChoose() {
        this.partOrComponent = true;
    }

    /**
     * 设置当前是为bom组件选择物料
     */
    public void setMaterialChooseComp() {
        this.partOrComponent = false;
    }

    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

            // String[] keyword = materialPattern.split(" ");
            //
            // for (int i = 0; i < keyword.length; i++) {
            // keyword[i] = keyword[i].trim();
            // }

            if (partOrComponent) {
                // 选择成品
                List<MaterialCategory> productCategories = //
                serviceFor(MaterialCategory.class).list( //
                        Or.of( //
                        new Equals("_classification", Classification.PRODUCT.getValue()), //
                                new Equals("_classification", Classification.SEMI.getValue()) //
                        ));
                List<Integer> ids = new ArrayList<Integer>();
                for (MaterialCategory c : productCategories) {
                    ids.add(c.getId());
                }

                List<Material> _materials = //
                serviceFor(Material.class).list( //
                        new Like("label", "%" + materialPattern + "%"), //
                        new InCollection("category.id", ids));

                materials = DTOs.marshalList(MaterialDto.class, _materials);
            } else {
                // 选择非成品(原材料，半成品，其他)
                List<MaterialCategory> productCategories = //
                serviceFor(MaterialCategory.class).list( //
                        Not.of(new Equals("_classification", Classification.PRODUCT.getValue())));
                List<Integer> ids = new ArrayList<Integer>();
                for (MaterialCategory c : productCategories) {
                    ids.add(c.getId());
                }

                List<Material> _materials = //
                serviceFor(Material.class).list( //
                        new Like("label", "%" + materialPattern + "%"), //
                        new InCollection("category.id", ids));

                materials = DTOs.marshalList(MaterialDto.class, _materials);
            }
        }
    }

    public void chooseMaterial() {
        if (partOrComponent) {
            // 检查此物料(成品)是否已经有bom存在

            List<Part> partList = serviceFor(Part.class).list(new Equals("target.id", selectedMaterial.getId()));

            if (partList != null & partList.size() > 0) {
                uiLogger.info("此物料已经存在BOM信息,继续新增的话,此物料将存在多个BOM信息");
                return;
            }

            part.setTarget(selectedMaterial);
        } else {
            List<Part> materialsIsPart = serviceFor(Part.class).list(new Equals("target.id", selectedMaterial.getId()));
            if (materialsIsPart != null && materialsIsPart.size() > 0) {
                uiLogger.info("此物料是成品或半成品，已经存在BOM，请用[组件是半成品]标签页进行查找选择!!!");
                return;
            }
            item.setMaterial(selectedMaterial);
            item.setPart(null);
        }

        selectedMaterial = null;
    }

    public void calcPrice()
            throws FxrQueryException {

        if (selectedPart == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }

        try {
            Part _part = selectedPart.unmarshal(this);
            price = _part.calcPrice();
        } catch (MaterialPriceNotFoundException e) {
            uiLogger.error("没有找到此产品的原材料原价格!", e);
        }
    }

    public void findPart() {
        if (partPattern != null && !partPattern.isEmpty()) {

            // String[] keyword = partPattern.split(" ");
            //
            // for (int i = 0; i < keyword.length; i++) {
            // keyword[i] = keyword[i].trim();
            // }

            List<Part> _parts = serviceFor(Part.class).list(BomCriteria.findPartUseMaterialName(partPattern));

            findedParts = DTOs.marshalList(PartDto.class, _parts);
        }
    }

    public void choosePart() {
        item.setMaterial(null);
        item.setPart(selectedFindedPart);

        selectedFindedPart = null;
    }

}
