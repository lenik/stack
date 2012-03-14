package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;

public class PurchaseRequestItemDto
        extends ProcessEntityDto<PurchaseRequestItem>
        implements IEnclosedObject<PurchaseRequestDto> {

    private static final long serialVersionUID = 1L;

    public static final int INQUIRIES = 1;

    PurchaseRequestDto parent;
    int index;
    MaterialDto material;
    BigDecimal requiredQuantity = new BigDecimal(0);
    BigDecimal quantity = new BigDecimal(0);

    PartyDto preferredSupplier;
    String additionalRequirement;

    List<PurchaseInquiryDto> inquiries;
    PurchaseInquiryDto acceptedInquiry;
    String comment;

    StockWarehouseDto destWarehouse;

    int inquiryCount;

    @Override
    protected void _marshal(PurchaseRequestItem source) {
        parent = mref(PurchaseRequestDto.class, source.getParent());
        index = source.getIndex();
        material = mref(MaterialDto.class, source.getMaterial());
        requiredQuantity = source.getRequiredQuantity();
        quantity = source.getQuantity();

        preferredSupplier = mref(PartyDto.class, source.getPreferredSupplier());
        additionalRequirement = source.getAdditionalRequirement();

        if (selection.contains(INQUIRIES))
            inquiries = marshalList(PurchaseInquiryDto.class, source.getInquiries());
        else
            inquiries = Collections.emptyList();

        acceptedInquiry = mref(PurchaseInquiryDto.class, source.getAcceptedInquiry());
        destWarehouse = mref(StockWarehouseDto.class, source.getDestWarehouse());

        inquiryCount = source.getInquiryCount();
    }

    @Override
    protected void _unmarshalTo(PurchaseRequestItem target) {
        merge(target, "parent", parent);
        target.setIndex(index);
        merge(target, "material", material);
        target.setRequiredQuantity(requiredQuantity);
        target.setQuantity(quantity);

        merge(target, "preferredSupplier", preferredSupplier);

        target.setAdditionalRequirement(additionalRequirement);

        if (selection.contains(INQUIRIES))
            mergeList(target, "inquiries", inquiries);

        merge(target, "acceptedInquiry", acceptedInquiry);
        merge(target, "destWarehouse", destWarehouse);

        target.setInquiryCount(inquiryCount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public PurchaseRequestDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(PurchaseRequestDto enclosingObject) {
        setParent(enclosingObject);
    }

    public PurchaseRequestDto getParent() {
        return parent;
    }

    public void setParent(PurchaseRequestDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
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

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public PurchaseInquiryDto getAcceptedInquiry() {
        return acceptedInquiry;
    }

    public void setAcceptedInquiry(PurchaseInquiryDto acceptedInquiry) {
        this.acceptedInquiry = acceptedInquiry;
    }


    public StockWarehouseDto getDestWarehouse() {
        return destWarehouse;
    }

    public void setDestWarehouse(StockWarehouseDto destWarehouse) {
        this.destWarehouse = destWarehouse;
    }

    public int getInquiryCount() {
        return inquiryCount;
    }

    public void setInquiryCount(int inquiryCount) {
        this.inquiryCount = inquiryCount;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(material));
    }

}
