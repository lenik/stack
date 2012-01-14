package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.purchase.entity.PurchaseAdvice;

public class PurchaseAdviceDto
        extends TxEntityDto<PurchaseAdvice>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    InquiryDto preferredInquiry;
    String reason;

    PurchaseRequestItemDto purchaseRequestItem;

    SingleVerifierSupportDto singleVerifierSupport;

    @Override
    protected void _marshal(PurchaseAdvice source) {
        preferredInquiry = mref(InquiryDto.class, source.getPreferredInquiry());
        reason = source.getReason();
        purchaseRequestItem = mref(PurchaseRequestItemDto.class, source.getPurchaseRequestItem());

        singleVerifierSupport = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());

    }

    @Override
    protected void _unmarshalTo(PurchaseAdvice target) {
        merge(target, "preferredInquiry", preferredInquiry);
        target.setReason(reason);
        merge(target, "purchaseRequestItem", purchaseRequestItem);

        merge(target, "verifyContext", singleVerifierSupport);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public InquiryDto getPreferredInquiry() {
        return preferredInquiry;
    }

    public void setPreferredInquiry(InquiryDto preferredInquiry) {
        this.preferredInquiry = preferredInquiry;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PurchaseRequestItemDto getPurchaseRequestItem() {
        return purchaseRequestItem;
    }

    public void setPurchaseRequestItem(
            PurchaseRequestItemDto purchaseRequestItem) {
        this.purchaseRequestItem = purchaseRequestItem;
    }

    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return singleVerifierSupport;
    }

    public void setVerifyContext(SingleVerifierSupportDto singleVerifierSupport) {
        this.singleVerifierSupport = singleVerifierSupport;
    }
}
