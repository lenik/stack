package com.bee32.sems.bom.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sems.bom.dto.PartDto;
import com.bee32.sems.bom.entity.Part;
import com.bee32.sems.bom.service.MaterialPriceNotFoundException;

@Scope("view")
public class BomAdminBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected static final int TAB_INDEX = 0;
    protected static final int TAB_FORM = 1;

    private boolean editable;
    private boolean editableComp;

    private int currTabComp;

    private PartDto product;
    private PartDto part;

    private PartDto selectedProduct;
    private PartDto selectedPart;

    private LazyDataModel<PartDto> products;

    private int flag;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private BigDecimal price;

    @PostConstruct
    public void init() {
       EntityDataModelOptions<Part, PartDto> options = new EntityDataModelOptions<Part, PartDto>(//
                Part.class, PartDto.class, TreeEntityDto.PARENT, //
                Order.desc("id"));
        products = UIHelper.<Part, PartDto> buildLazyDataModel(options);

        refreshProductCount();

        editable = false;

        currTabComp = 0;
        editableComp = false;
    }

    void refreshProductCount() {
        int count = serviceFor(Part.class).count();
        products.setRowCount(count);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditableComp() {
        return editableComp;
    }

    public void setEditableComp(boolean editableComp) {
        this.editableComp = editableComp;
    }

    public PartDto getPart() {
        if (part == null) {
            newPart();
        }
        return part;
    }

    private void newPart() {
        part = new PartDto().create();

        part.setValid(true);
    }

    public void setPart(PartDto part) {
        this.part = part;
    }

    public PartDto getProduct() {
        if (product == null) {
            newProduct();
        }
        return product;
    }

    private void newProduct() {
        product = new PartDto().create();
    }

    public void setProduct(PartDto product) {
        this.product = product;
    }

    public PartDto getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(PartDto selectedPart) {
        this.selectedPart = selectedPart;
    }

    public PartDto getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(PartDto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public int getCurrTabComp() {
        return currTabComp;
    }

    public void setCurrTabComp(int currTabComp) {
        this.currTabComp = currTabComp;
    }

    public LazyDataModel<PartDto> getProducts() {
        return products;
    }

    public void setProducts(LazyDataModel<PartDto> products) {
        this.products = products;
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

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isProductSelected() {
        if (selectedProduct != null)
            return true;
        return false;
    }

    public void new_() {
        setActiveTab(TAB_FORM);
        editable = true;

        newProduct();
    }

    public void delete_() {
        if (selectedProduct == null) {
            uiLogger.info("请以单击选择需要删除的产品!");
            return;
        }

        try {
            int childrenCount = serviceFor(Part.class).count(new Equals("parent.id", selectedProduct.getId()));
            if (childrenCount != 0) {
                uiLogger.error("此产品已经包含组件，必须先删除相应组件，才能删除本产品!");
                return;
            }

            serviceFor(Part.class).deleteById(selectedProduct.getId());

            uiLogger.info("删除产品成功");
        } catch (Exception e) {
            uiLogger.error("删除产品失败;", e);
        }

    }

    public void save_() {
        try {
            product.setLastModified(new Date());

            Part _product = product.unmarshal(this);
            serviceFor(Part.class).saveOrUpdate(_product);

            refreshProductCount();

            setActiveTab(TAB_INDEX);
            editable = false;
            uiLogger.info("产品保存成功");
        } catch (Exception e) {
            uiLogger.error("保存产品失败", e);
        }
    }

    public void cancel_() {
        setActiveTab(TAB_INDEX);
        editable = false;
        newProduct();
    }

    public void detail_() {
        if (selectedProduct == null) {
            uiLogger.info("请以单击选择需要查看详细内容的产品");
            return;
        }

        setActiveTab(TAB_FORM);
        product = selectedProduct;
    }

    public void refresh_() {
        setActiveTab(TAB_INDEX);
        selectedProduct = null;
        refreshProductCount();
    }

    public void onRowSelect(SelectEvent event) {

    }

    public void onRowUnselect(UnselectEvent event) {

    }

    public void newComp_() {
        if (product == null || product.getId() == null) {
            uiLogger.error("请选择相应的产品!");
            return;
        }

        currTabComp = 1;
        editableComp = true;

        newPart();
    }

    public void deleteComp_() {
        if (selectedPart == null) {
            uiLogger.error("请以单击选择需要删除的BOM组件!");
            return;
        }

        try {
            serviceFor(Part.class).deleteById(selectedPart.getId());

            uiLogger.info("删除BOM组件成功");
        } catch (Exception e) {
            uiLogger.error("删除BOM组件失败", e);
        }
    }

    public void saveComp_() {
        if (product == null || product.getId() == null) {
            uiLogger.error("请选择相应的产品!");
            return;
        }

        try {
            PartDto parentRef = new PartDto().ref(product);
            part.setParent(parentRef);

            Part _part = part.unmarshal(this);
            serviceFor(Part.class).saveOrUpdate(_part);

            currTabComp = 0;
            editableComp = false;
            uiLogger.info("保存BOM组件成功");
        } catch (Exception e) {
            uiLogger.error("保存BOM组件失败", e);
        }
    }

    public void cancelComp_() {
        currTabComp = 0;
        editableComp = false;

        newPart();
    }

    public void detailComp_() {
        if (selectedPart == null) {
            uiLogger.error("请以单击选择需要查看详细内容的BOM组件!");
            return;
        }

        currTabComp = 1;
        part = selectedPart;
    }

    public void refreshComp_() {
        currTabComp = 0;
        selectedPart = null;
    }

    public List<PartDto> getParts() {
        List<Part> _children;

        if (product != null && product.getId() != null)
            _children = serviceFor(Part.class).list(//
                    TreeCriteria.childOf(product.getId()));
        else
            _children = new ArrayList<Part>();

        List<PartDto> children = DTOs.marshalList(PartDto.class, 0, _children, true);
        return children;
    }

    public void setMaterialChoose() {
        this.flag = 0;
    }

    public void setMaterialChooseComp() {
        this.flag = 1;
    }

    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

//            String[] keyword = materialPattern.split(" ");
//
//            for (int i = 0; i < keyword.length; i++) {
//                keyword[i] = keyword[i].trim();
//            }

            List<Material> _materials = serviceFor(Material.class).list(new Like("name", "%" + materialPattern + "%"));

            materials = DTOs.marshalList(MaterialDto.class, _materials);
        }
    }

    public void chooseMaterial() {
        if (flag == 0)
            product.setMaterial(selectedMaterial);
        if (flag == 1)
            part.setMaterial(selectedMaterial);
    }

    public void calcPrice()
            throws FxrQueryException {

        if (selectedProduct == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }

        try {
            Part _product = selectedProduct.unmarshal(this);
            price = _product.calcPrice();
        } catch (MaterialPriceNotFoundException e) {
            uiLogger.error("没有找到此产品的原材料原价格!", e);
        }
    }

}
