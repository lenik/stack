package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;

public class PurchaseRequestItemDto
        extends MomentIntervalDto<PurchaseRequestItem>
        implements IEnclosedObject<PurchaseRequestDto> {

    private static final long serialVersionUID = 1L;

    public static final int INQUIRIES = 1;

    PurchaseRequestDto purchaseRequest;
    int index;
    MaterialDto material;
    BigDecimal quantity = new BigDecimal(0);
    BigDecimal planQuantity = new BigDecimal(0);

    PartyDto preferredSupplier;
    String additionalRequirement;

    List<PurchaseInquiryDto> inquiries;
    PurchaseAdviceDto purchaseAdvice;

    Integer warehouseId; // 公用于接收界面上传入的仓库id

    @Override
    protected void _marshal(PurchaseRequestItem source) {
        purchaseRequest = mref(PurchaseRequestDto.class, source.getPurchaseRequest());
        index = source.getIndex();
        material = mref(MaterialDto.class, source.getMaterial());
        quantity = source.getQuantity();
        planQuantity = source.getPlanQuantity();

        preferredSupplier = mref(PartyDto.class, source.getPreferredSupplier());
        additionalRequirement = source.getAdditionalRequirement();

        if (selection.contains(INQUIRIES))
            inquiries = marshalList(PurchaseInquiryDto.class, source.getInquiries());
        else
            inquiries = new ArrayList<PurchaseInquiryDto>();

        purchaseAdvice = mref(PurchaseAdviceDto.class, source.getPurchaseAdvice());
    }

    @Override
    protected void _unmarshalTo(PurchaseRequestItem target) {
        merge(target, "purchaseRequest", purchaseRequest);
        target.setIndex(index);
        merge(target, "material", material);
        target.setQuantity(quantity);
        target.setPlanQuantity(planQuantity);

        merge(target, "preferredSupplier", preferredSupplier);

        target.setAdditionalRequirement(additionalRequirement);

        if (selection.contains(INQUIRIES))
            mergeList(target, "inquiries", inquiries);

        merge(target, "purchaseAdvice", purchaseAdvice);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public PurchaseRequestDto getEnclosingObject() {
        return getPurchaseRequest();
    }

    @Override
    public void setEnclosingObject(PurchaseRequestDto enclosingObject) {
        setPurchaseRequest(enclosingObject);
    }

    public PurchaseRequestDto getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequestDto purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public PartyDto getPreferredSupplier() {
        return preferredSupplier;
    }

    public void setPreferredSupplier(PartyDto preferredSupplier) {
        this.preferredSupplier = preferredSupplier;
    }

    @NLength(max = PurchaseRequestItem.ADDITIONAL_REQUIREMENT_LENGTH)
    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = TextUtil.normalizeSpace(additionalRequirement);
    }

    public List<PurchaseInquiryDto> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<PurchaseInquiryDto> inquiries) {
        this.inquiries = inquiries;
    }

    public synchronized void addInquiry(PurchaseInquiryDto inquiry) {
        if (inquiry == null)
            throw new NullPointerException("inquiry");

        inquiries.add(inquiry);
    }

    public synchronized void removeInquiry(PurchaseInquiryDto inquiry) {
        if (inquiry == null)
            throw new NullPointerException("inquiry");

        int index = inquiries.indexOf(inquiry);
        if (index == -1)
            return /* false */;

        inquiries.remove(index);
        // inquiry.detach();
    }

    public PurchaseAdviceDto getPurchaseAdvice() {
        return purchaseAdvice;
    }

    public void setPurchaseAdvice(PurchaseAdviceDto purchaseAdvice) {
        this.purchaseAdvice = purchaseAdvice;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(purchaseRequest), //
                naturalId(material));
    }
}
