package com.bee32.sem.purchase.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.purchase.dto.PurchaseInquiryDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.PurchaseInquiry;

public class ChoosePurchaseInquiryDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePurchaseInquiryDialogBean.class);

    String header = "选择采购询价…";
    PurchaseRequestItemDto parent;

    public ChoosePurchaseInquiryDialogBean() {
        super(PurchaseInquiry.class, PurchaseInquiryDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (!DTOs.isNull(parent))
            elements.add(new Equals("parent.id", parent.getId()));
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public PurchaseRequestItemDto getParent() {
        return parent;
    }

    public void setParent(PurchaseRequestItemDto parent) {
        this.parent = parent;
    }

}
