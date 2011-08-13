package com.bee32.sems.bom.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.user.util.SessionLoginInfo;
import com.bee32.sems.bom.dto.ComponentDTO;
import com.bee32.sems.bom.dto.ProductDTO;
import com.bee32.sems.bom.service.BomServiceFacade;
import com.bee32.sems.bom.service.MaterialPriceNotFoundException;
import com.bee32.sems.material.dto.MaterialDTO;
import com.bee32.sems.material.service.MaterialServiceFacade;
import com.bee32.sems.org.dao.PersonDao;
import com.bee32.sems.org.dto.PersonDTO;
import com.bee32.sems.org.entity.Person;
import org.primefaces.event.UnselectEvent;

@Component
@Scope("view")
public class BomAdminBean implements Serializable {
    private boolean editable;
    private boolean editableComp;

    private int currTab;
    private int currTabComp;

    private ProductDTO product;
    private ComponentDTO component;

    private ProductDTO selectedProduct;
    private ComponentDTO selectedComponent;

    private LazyDataModel<ProductDTO> products;

    private int flag;

    private String materialPartten;
    private List<MaterialDTO> materials;
    private MaterialDTO selectedMaterial;

    private Double price;



    @PostConstruct
    public void init() {
        //这里必须定义了两个BomServiceFacade,不能只定义一个final的BomServiceFacade,
        //因为会造成jsf的序列化问题
        products = new LazyDataModel<ProductDTO>() {
            @Override
            public List<ProductDTO> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
                BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");
                return bomServiceFacade.listProduct(first, pageSize);
            }
        };

        BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");
        products.setRowCount((int) bomServiceFacade.productCount());

        currTab = 0;
        editable = false;

        currTabComp = 0;
        editableComp = false;
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

    public ComponentDTO getComponent() {
        if(component == null) {
            newComponent();
        }
        return component;
    }

    private void newComponent() {
        component = new ComponentDTO();

        MaterialDTO material = new MaterialDTO();
        component.setMaterial(material);
        component.setValid(true);
    }

    public void setComponent(ComponentDTO component) {
        this.component = component;
    }

    public ProductDTO getProduct() {
        if(product == null) {
            newProduct();
        }
        return product;
    }

    private void newProduct() {
        product = new ProductDTO();

        HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getCurrentUser(session);
        PersonDao personDao
                        = (PersonDao) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("personDao");
        Person person = personDao.load(currUser.getId());
        PersonDTO creator = new PersonDTO().marshal(person);
        product.setCreator(creator);

        MaterialDTO material = new MaterialDTO();
        product.setMaterial(material);
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ComponentDTO getSelectedComponent() {
        return selectedComponent;
    }

    public void setSelectedComponent(ComponentDTO selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    public ProductDTO getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductDTO selectedProduct) {
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

    public LazyDataModel<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(LazyDataModel<ProductDTO> products) {
        this.products = products;
    }

    public String getMaterialPartten() {
        return materialPartten;
    }

    public void setMaterialPartten(String materialPartten) {
        this.materialPartten = materialPartten;
    }

    public List<MaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDTO> materials) {
        this.materials = materials;
    }

    public MaterialDTO getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDTO selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public Double getPrice() {
        return price;
    }

    public boolean isProductSelected() {
        if(selectedProduct != null)
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
            context.addMessage(null, new FacesMessage("提示", "请以单击选择需要删除的产品!"));
            return;
        }

        try {
            BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");

            List<ComponentDTO> components = bomServiceFacade.listComponent(selectedProduct.getId());
            if(components != null && components.size() > 0) {
                context.addMessage(null, new FacesMessage("提示", "此产品已经包含组件，必须先删除相应组件，才能删除本产品!"));
                return;
            }

            bomServiceFacade.deleteProduct(selectedProduct.getId());

            products.setRowCount((int) bomServiceFacade.productCount());


            context.addMessage(null, new FacesMessage("提示", "删除产品成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除产品失败;" + e.getMessage()));
        }

    }

    public void save_() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");

            product.setDate(new Date());

            bomServiceFacade.addProduct(product);

            products.setRowCount((int) bomServiceFacade.productCount());

            currTab = 0;
            editable = false;
            context.addMessage(null, new FacesMessage("提示", "产品保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "保存产品失败" + e.getMessage()));
        }
    }

    public void cancel_() {
        currTab = 0;
        editable = false;

        newProduct();
    }

    public void detail_() {
        if(selectedProduct == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请以单击选择需要查看详细内容的产品"));
            return;
        }

        currTab = 1;
        product = selectedProduct;
    }

    public void refresh_() {
        currTab = 0;
        BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");

        selectedProduct = null;
        products.setRowCount((int) bomServiceFacade.productCount());
    }

    public void onRowSelect(SelectEvent event) {

    }

    public void onRowUnselect(UnselectEvent event) {

    }

    public void newComp_() {
        FacesContext context = FacesContext.getCurrentInstance();
        if(product == null || product.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择相应的产品!"));
            return;
        }

        currTabComp = 1;
        editableComp = true;

        newComponent();
    }

    public void deleteComp_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedComponent == null) {
            context.addMessage(null, new FacesMessage("提示", "请以单击选择需要删除的BOM组件!"));
            return;
        }

        try {
            BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");

            bomServiceFacade.deleteComponent(selectedComponent.getId());

            context.addMessage(null, new FacesMessage("提示", "删除BOM组件成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除BOM组件失败" + e.getMessage()));
        }
    }

    public void saveComp_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(product == null || product.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择相应的产品!"));
            return;
        }

        try {
            BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");

            component.setProductId(product.getId());
            bomServiceFacade.addComponent(component);


            currTabComp = 0;
            editableComp = false;
            context.addMessage(null, new FacesMessage("提示", "保存BOM组件成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "保存BOM组件失败" + e.getMessage()));
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
            context.addMessage(null, new FacesMessage("提示", "请以单击选择需要查看详细内容的BOM组件!"));
            return;
        }

        currTabComp = 1;
        component = selectedComponent;
    }

    public void refreshComp_() {
        currTabComp = 0;
        selectedComponent = null;
    }

    public List<ComponentDTO> getComponents() {
        List<ComponentDTO> components = new ArrayList<ComponentDTO>();

        if(product != null && product.getId() != null) {
            BomServiceFacade bomServiceFacade
                    = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");
            components = bomServiceFacade.listComponent(product.getId());
        }

        return components;
    }


    public void setMaterialChoose() {
        this.flag = 0;
    }

    public void setMaterialChooseComp() {
        this.flag = 1;
    }


    public void findMaterial() {
        if(materialPartten != null && !materialPartten.isEmpty()) {
            MaterialServiceFacade materialServiceFacade
                        = (MaterialServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("materialServiceFacadeImpl");

            String[] keyword = materialPartten.split(" ");

            for (int i = 0; i < keyword.length; i++) {
                keyword[i] = keyword[i].trim();
            }

            materials = materialServiceFacade.findMaterial(keyword);

        }
    }

    public void chooseMaterial() {
        if(flag == 0)
            product.setMaterial(selectedMaterial);
        if(flag == 1)
            component.setMaterial(selectedMaterial);
    }

    public void calcPrice() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedProduct == null) {
            context.addMessage(null, new FacesMessage("提示", "请以单击选择需要计算价格的产品!"));
            return;
        }

        BomServiceFacade bomServiceFacade
                = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");

        try {
            price = bomServiceFacade.productPrice(selectedProduct);
        } catch (MaterialPriceNotFoundException e) {
            context.addMessage(null, new FacesMessage("提示", "没有找到此产品的原材料原价格!"));
        }
    }
}
