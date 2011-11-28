package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.entity.Inquiry;
import com.bee32.sem.world.monetary.MCValue;

public class InquiryDto
        extends TxEntityDto<Inquiry>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;


    PartyDto party;
    MCValue price;
    Date deliveryDate;
    String quality;
    String paymentTerm;
    String afterService;
    String other;

    PurchaseRequestItemDto purchaseReqeustItem;

    PurchaseAdviceDto purchaseAdvice;

    @Override
    protected void _marshal(Inquiry source) {
        party = mref(PartyDto.class, source.getParty());
        price = source.getPrice();
        deliveryDate = source.getDeliveryDate();
        quality = source.getQuality();
        paymentTerm = source.getPaymentTerm();
        afterService = source.getAfterService();
        other = source.getOther();

        purchaseReqeustItem = mref(PurchaseRequestItemDto.class,
                source.getPurcheaseReqeustItem());

        purchaseAdvice = mref(PurchaseAdviceDto.class,
                source.getPurchaseAdvice());

    }

    @Override
    protected void _unmarshalTo(Inquiry target) {
        merge(target, "party", party);
        target.setPrice(price);
        target.setDeliveryDate(deliveryDate);
        target.setQuality(quality);
        target.setPaymentTerm(paymentTerm);
        target.setAfterService(afterService);
        target.setOther(other);

        merge(target, "purchaseReqeustItem", purchaseReqeustItem);

        merge(target, "purchaseAdvice", purchaseAdvice);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        this.price = price;
    }

    public BigDecimal getPriceDigit() {
        return price.getValue();
    }

    public void setPirceDigit(BigDecimal priceDigit) {
        price = new MCValue(price.getCurrency(), priceDigit);
    }

    public String getPriceCurrency() {
        if (price.getCurrency() == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return price.getCurrency().getCurrencyCode();
    }

    public void setPriceCurrency(String currencyCode) {
        price = new MCValue(Currency.getInstance(currencyCode), price.getValue());
    }


    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
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

    public PurchaseRequestItemDto getPurchaseReqeustItem() {
        return purchaseReqeustItem;
    }

    public void setPurchaseReqeustItem(PurchaseRequestItemDto purchaseReqeustItem) {
        this.purchaseReqeustItem = purchaseReqeustItem;
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
                naturalId(party), //
                naturalId(purchaseReqeustItem));
    }
}
