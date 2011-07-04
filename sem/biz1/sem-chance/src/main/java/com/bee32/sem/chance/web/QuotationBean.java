package com.bee32.sem.chance.web;

import java.util.List;

import javax.annotation.PostConstruct;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.QuotationDto;
import com.bee32.sem.chance.entity.Quotation;

public class QuotationBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private boolean editable;
    private boolean detailable;
    private boolean orderable;

    private List<QuotationDto> quotations;
    private QuotationDto selectedQuotation;
    private QuotationDto quotation;

    private String materialPattern;
    private String[] selectedMaterial;
    private List<String> material;

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

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public String[] getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(String[] selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }
}
