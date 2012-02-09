package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.purchase.entity.PurchaseAdvice;

public class PurchaseAdviceDto
        extends TxEntityDto<PurchaseAdvice>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    PurchaseRequestItemDto purchaseRequestItem;
    PurchaseInquiryDto preferredInquiry;
    String reason;

    SingleVerifierSupportDto singleVerifierSupport;

    @Override
    protected void _marshal(PurchaseAdvice source) {
        purchaseRequestItem = mref(PurchaseRequestItemDto.class, source.getPurchaseRequestItem());
        preferredInquiry = mref(PurchaseInquiryDto.class, source.getPreferredInquiry());
        reason = source.getReason();

        singleVerifierSupport = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(PurchaseAdvice target) {
        merge(target, "purchaseRequestItem", purchaseRequestItem);
        merge(target, "preferredInquiry", preferredInquiry);
        target.setReason(reason);

        merge(target, "verifyContext", singleVerifierSupport);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PurchaseRequestItemDto getPurchaseRequestItem() {
        return purchaseRequestItem;
    }

    public void setPurchaseRequestItem(PurchaseRequestItemDto purchaseRequestItem) {
        this.purchaseRequestItem = purchaseRequestItem;
    }

    public PurchaseInquiryDto getPreferredInquiry() {
        return preferredInquiry;
    }

    public void setPreferredInquiry(PurchaseInquiryDto preferredInquiry) {
        this.preferredInquiry = preferredInquiry;
    }

    @NLength(max = PurchaseAdvice.REASON_LENGTH)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = TextUtil.normalizeSpace(reason);
    }

    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return singleVerifierSupport;
    }

    public void setVerifyContext(SingleVerifierSupportDto singleVerifierSupport) {
        this.singleVerifierSupport = singleVerifierSupport;
    }
}
