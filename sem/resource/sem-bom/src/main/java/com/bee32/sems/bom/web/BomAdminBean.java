package com.bee32.sems.bom.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sems.bom.dto.ComponentDto;
import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.bom.service.MaterialPriceNotFoundException;
import com.bee32.sems.bom.service.MaterialPriceStrategy;

@Scope("view")
public class BomAdminBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    private boolean editable;
    private boolean editableComp;

    private int currTab;
    private int currTabComp;

    private ComponentDto product;
    private ComponentDto component;

    private ComponentDto selectedProduct;
    private ComponentDto selectedComponent;

    private LazyDataModel<ComponentDto> products;

    private int flag;

    private String materialPartten;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private BigDecimal price;

    @PostConstruct
    public void init() {

        EntityDataModelOptions<Component, ComponentDto> options = new EntityDataModelOptions<Component, ComponentDto>(//
                Component.class, ComponentDto.class, 0, //
                Order.desc("id"), //
                TreeCriteria.root(), //
                EntityCriteria.ownedByCurrentUser());
        products = UIHelper.<Component, ComponentDto> buildLazyDataModel(options);

        refreshProductCount();

        currTab = 0;
        editable = false;

        currTabComp = 0;
        editableComp = false;
    }

    void refreshProductCount() {
        int count = serviceFor(Component.class).count(//
                TreeCriteria.root(), //
                EntityCriteria.ownedByCurrentUser());
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

    public ComponentDto getComponent() {
        if (component == null) {
            newComponent();
        }
        return component;
    }

    private void newComponent() {
        component = new ComponentDto();

        MaterialDto material = new MaterialDto();
        component.setMaterial(material);
        component.setValid(true);
    }

    public void setComponent(ComponentDto component) {
        this.component = component;
    }

    public ComponentDto getProduct() {
        if (product == null) {
            newProduct();
        }
        return product;
    }

    private void newProduct() {
        product = new ComponentDto();

        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getUser();

        MaterialDto material = new MaterialDto();
        product.setMaterial(material);
    }

    public void setProduct(ComponentDto product) {
        this.product = product;
    }

    public ComponentDto getSelectedComponent() {
        return selectedComponent;
    }

    public void setSelectedComponent(ComponentDto selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    public ComponentDto getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ComponentDto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public int getCurrTab() {
        return currTab;
    }

    public void setCurrTab(int currTab) {
        this.currTab = currTab;
    }

    public int getCurrTabComp() {
        return currTabComp;
    }

    public void setCurrTabComp(int currTabComp) {
        this.currTabComp = currTabComp;
    }

    public LazyDataModel<ComponentDto> getProducts() {
        return products;
    }

    public void setProducts(LazyDataModel<ComponentDto> products) {
        this.products = products;
    }

    public String getMaterialPartten() {
        return materialPartten;
    }

    public void setMaterialPartten(String materialPartten) {
        this.materialPartten = materialPartten;
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
        currTab = 1;
        editable = true;

        newProduct();
    }

    public void delete_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedProduct == null) {
            uiLogger.info("请以单击选择需要删除的产品!");
            return;
        }

        try {
            int childrenCount = serviceFor(Component.class).count(new Equals("parent.id", selectedProduct.getId()));
            if (childrenCount != 0) {
                uiLogger.error("此产品已经包含组件，必须先删除相应组件，才能删除本产品!");
                return;
            }

            serviceFor(Component.class).deleteById(selectedProduct.getId());

            refreshProductCount();

            uiLogger.info("删除产品成功");
        } catch (Exception e) {
            uiLogger.error("删除产品失败;", e);
        }

    }

    public void save_() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            product.setLastModified(new Date());

            Component _product = product.unmarshal(this);
            serviceFor(Component.class).saveOrUpdate(_product);

            refreshProductCount();

            currTab = 0;
            editable = false;
            uiLogger.info("产品保存成功");
        } catch (Exception e) {
            uiLogger.error("保存产品失败", e);
        }
    }

    public void cancel_() {
        currTab = 0;
        editable = false;

        newProduct();
    }

    public void detail_() {
        if (selectedProduct == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            uiLogger.info("请以单击选择需要查看详细内容的产品");
            return;
        }

        currTab = 1;
        product = selectedProduct;
    }

    public void refresh_() {
        currTab = 0;
        selectedProduct = null;
        refreshProductCount();
    }

    public void onRowSelect(SelectEvent event) {

    }

    public void onRowUnselect(UnselectEvent event) {

    }

    public void newComp_() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (product == null || product.getId() == null) {
            uiLogger.error("请选择相应的产品!");
            return;
        }

        currTabComp = 1;
        editableComp = true;

        newComponent();
    }

    public void deleteComp_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedComponent == null) {
            uiLogger.error("请以单击选择需要删除的BOM组件!");
            return;
        }

        try {
            serviceFor(Component.class).deleteById(selectedComponent.getId());

            uiLogger.info("删除BOM组件成功");
        } catch (Exception e) {
            uiLogger.error("删除BOM组件失败", e);
        }
    }

    public void saveComp_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (product == null || product.getId() == null) {
            uiLogger.error("请选择相应的产品!");
            return;
        }

        try {
            ComponentDto parentRef = new ComponentDto().ref(product);
            component.setParent(parentRef);

            Component _component = component.unmarshal(this);
            serviceFor(Component.class).saveOrUpdate(_component);

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

        newComponent();
    }

    public void detailComp_() {
        if (selectedComponent == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            uiLogger.error("请以单击选择需要查看详细内容的BOM组件!");
            return;
        }

        currTabComp = 1;
        component = selectedComponent;
    }

    public void refreshComp_() {
        currTabComp = 0;
        selectedComponent = null;
    }

    public List<ComponentDto> getComponents() {
        List<Component> _children;

        if (product != null && product.getId() != null)
            _children = serviceFor(Component.class).list(//
                    TreeCriteria.childOf(product.getId()));
        else
            _children = new ArrayList<Component>();

        List<ComponentDto> children = DTOs.marshalList(ComponentDto.class, 0, _children, true);
        return children;
    }

    public void setMaterialChoose() {
        this.flag = 0;
    }

    public void setMaterialChooseComp() {
        this.flag = 1;
    }

    public void findMaterial() {
        if (materialPartten != null && !materialPartten.isEmpty()) {

            String[] keyword = materialPartten.split(" ");

            for (int i = 0; i < keyword.length; i++) {
                keyword[i] = keyword[i].trim();
            }

            List<Material> _materials = serviceFor(Material.class).list(//
                    // TODO MaterialCriteria.namedLike(keyword)
                    );
            materials = DTOs.marshalList(MaterialDto.class, 0, _materials, true);
        }
    }

    public void chooseMaterial() {
        if (flag == 0)
            product.setMaterial(selectedMaterial);
        if (flag == 1)
            component.setMaterial(selectedMaterial);
    }

    public void calcPrice() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedProduct == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }

        MaterialPriceStrategy priceStrategy = MaterialPriceStrategy.getDefault();

        try {
            Component _product = selectedProduct.unmarshal(this);
            price = priceStrategy.getPrice(this, _product);
        } catch (MaterialPriceNotFoundException e) {
            uiLogger.error("没有找到此产品的原材料原价格!");
        }
    }

}
