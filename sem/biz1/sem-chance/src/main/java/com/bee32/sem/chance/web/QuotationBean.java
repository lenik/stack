package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Order;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.QuotationDto;
import com.bee32.sem.chance.dto.QuotationItemDto;
import com.bee32.sem.chance.entity.BasePrice;
import com.bee32.sem.chance.entity.Quotation;
import com.bee32.sem.chance.entity.QuotationItem;
import com.bee32.sem.chance.util.PriceCriteria;

public class QuotationBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private boolean editable;
    private boolean detailable;
    private boolean orderable;

    private List<QuotationDto> quotations;
    private QuotationDto selectedQuotation;
    private QuotationDto quotation;
    private List<String> materials;
    private String selectedMaterial;
    private String materialPattern;

    QuotationBean() {
        initMaterial();
    }

    void initMaterial() {
        materials = new ArrayList<String>();
        materials.add("尼康D200");
        materials.add("松下GF3");
        materials.add("佳能D300");
        materials.add("宾得XR");
        materials.add("猪肉");
    }

    @PostConstruct
    public void init() {
        List<Quotation> lq = serviceFor(Quotation.class).list();
        quotations = DTOs.marshalList(QuotationDto.class, lq);
        quotation = new QuotationDto().ref(new Quotation());
    }

    public void onRowSelect() {
    }

    public void onRowUnselect() {
    }

    public void viewDetail() {
        if (selectedQuotation != null)
            quotation = selectedQuotation;
    }

    public void findMaterial() {
        if (!materialPattern.isEmpty()) {
            List<String> temp = new ArrayList<String>();
            initMaterial();
            for (String s : materials) {
                if (s.contains(materialPattern))
                    temp.add(s);
            }
            setMaterials(temp);
        } else {
            initMaterial();
        }
    }

    public void chooseMaterial() {
        String sm = selectedMaterial;
        BasePrice currentPrice = serviceFor(BasePrice.class).list(//
                Order.desc("createdDate"), PriceCriteria.listBasePriceByMaterial(sm)).get(0);
        QuotationItem qi = new QuotationItem();
        qi.setQuotation(quotation.unmarshal());
        qi.setBasePrice(currentPrice);
        qi.setMaterial(sm);
// qi.setDiscount()
// qi.setPrice();
// qi.setNumber()
        QuotationItemDto qid = DTOs.mref(QuotationItemDto.class, qi);
        quotation.addItem(qid);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isDetailable() {
        return detailable;
    }

    public void setDetailable(boolean detailable) {
        this.detailable = detailable;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public void setOrderable(boolean orderable) {
        this.orderable = orderable;
    }

    public List<QuotationDto> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<QuotationDto> quotations) {
        this.quotations = quotations;
    }

    public QuotationDto getSelectedQuotation() {
        return selectedQuotation;
    }

    public void setSelectedQuotation(QuotationDto selectedQuotation) {
        this.selectedQuotation = selectedQuotation;
    }

    public QuotationDto getQuotation() {
        return quotation;
    }

    public void setQuotation(QuotationDto quotation) {
        this.quotation = quotation;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public String getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(String selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

}
