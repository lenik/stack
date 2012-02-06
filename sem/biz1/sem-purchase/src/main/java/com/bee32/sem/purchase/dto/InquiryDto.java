package com.bee32.sem.purchase.dto;

import java.io.Serializable;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;
import org.zkoss.idom.Textual;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.purchase.entity.Inquiry;
import com.bee32.sem.world.monetary.MutableMCValue;

public class InquiryDto
        extends TxEntityDto<Inquiry>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    OrgDto org;
    MutableMCValue price;
    String deliveryDate;
    String quality;
    String paymentTerm;
    String afterService;
    String other;

    PurchaseRequestItemDto purchaseRequestItem;

    PurchaseAdviceDto purchaseAdvice;

    @Override
    protected void _marshal(Inquiry source) {
        org = mref(OrgDto.class, source.getOrg());
        price = source.getPrice().toMutable();
        deliveryDate = source.getDeliveryDate();
        quality = source.getQuality();
        paymentTerm = source.getPaymentTerm();
        afterService = source.getAfterService();
        other = source.getOther();

        purchaseRequestItem = mref(PurchaseRequestItemDto.class, source.getPurchaseRequestItem());

        purchaseAdvice = mref(PurchaseAdviceDto.class, source.getPurchaseAdvice());

    }

    @Override
    protected void _unmarshalTo(Inquiry target) {
        merge(target, "org", org);
        target.setPrice(price);
        target.setDeliveryDate(deliveryDate);
        target.setQuality(quality);
        target.setPaymentTerm(paymentTerm);
        target.setAfterService(afterService);
        target.setOther(other);

        merge(target, "purchaseRequestItem", purchaseRequestItem);

        merge(target, "purchaseAdvice", purchaseAdvice);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public OrgDto getOrg() {
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

    public MutableMCValue getPrice() {
        return price;
    }

    public void setPrice(MutableMCValue price) {
        this.price = price;
    }

    @NLength(max = Inquiry.DELIVERY_DATE_LENGTH)
    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = TextUtil.normalizeSpace(deliveryDate);
    }

    @NLength(max = Inquiry.QUALITY_LENGTH)
    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = TextUtil.normalizeSpace(quality);
    }

    @NLength(max = Inquiry.PAYMENT_TERM_LENGTH)
    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = TextUtil.normalizeSpace(paymentTerm);
    }

    @NLength(max = Inquiry.AFTER_SERVICE_LENGTH)
    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = TextUtil.normalizeSpace(afterService);
    }

    @NLength(max = Inquiry.OTHER_LENGTH)
    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = TextUtil.normalizeSpace(other);
    }

    public PurchaseRequestItemDto getPurchaseRequestItem() {
        return purchaseRequestItem;
    }

    public void setPurchaseRequestItem(PurchaseRequestItemDto purchaseRequestItem) {
        this.purchaseRequestItem = purchaseRequestItem;
    }

    public PurchaseAdviceDto getPurchaseAdvice() {
        return purchaseAdvice;
    }

    public void setPurchaseAdvice(PurchaseAdviceDto purchaseAdvice) {
        this.purchaseAdvice = purchaseAdvice;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(org), //
                naturalId(purchaseRequestItem));
    }
}
