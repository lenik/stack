package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.purchase.entity.Inquiry;
import com.bee32.sem.world.monetary.MCValue;

public class InquiryDto
        extends TxEntityDto<Inquiry>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;


    OrgDto org;
    MCValue price;
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
        price = source.getPrice();
        deliveryDate = source.getDeliveryDate();
        quality = source.getQuality();
        paymentTerm = source.getPaymentTerm();
        afterService = source.getAfterService();
        other = source.getOther();

        purchaseRequestItem = mref(PurchaseRequestItemDto.class,
                source.getPurchaseRequestItem());

        purchaseAdvice = mref(PurchaseAdviceDto.class,
                source.getPurchaseAdvice());

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
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public OrgDto getOrg() {
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        this.price = price;
    }

    public BigDecimal getPriceDigit() {
        if (price == null)
            return new BigDecimal(0);
        return price.getValue();
    }

    public void setPriceDigit(BigDecimal priceDigit) {
        Currency c = CurrencyConfig.getNative();
        if (price != null)
            c = price.getCurrency();

        price = new MCValue(c, priceDigit);
    }

    public String getPriceCurrency() {
        if (price == null)
            return CurrencyConfig.getNative().getCurrencyCode();

        if (price.getCurrency() == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return price.getCurrency().getCurrencyCode();
    }

    public void setPriceCurrency(String currencyCode) {
        if (price == null)
            price = new MCValue(Currency.getInstance(currencyCode), 0);
        else
            price = new MCValue(Currency.getInstance(currencyCode), price.getValue());
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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
