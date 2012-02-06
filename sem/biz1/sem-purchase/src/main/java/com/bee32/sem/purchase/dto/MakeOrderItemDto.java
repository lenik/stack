package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.purchase.entity.MakeOrderItem;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MutableMCValue;

public class MakeOrderItemDto
        extends CEntityDto<MakeOrderItem, Long>
        implements IEnclosedObject<MakeOrderDto> {

    private static final long serialVersionUID = 1L;

    MakeOrderDto order;
    int index;
    PartDto part;
    Date deadline;
    BigDecimal quantity = new BigDecimal(1);
    MutableMCValue price;

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    String externalProductName;
    String externalSpecification;

    @Override
    protected void _marshal(MakeOrderItem source) {
        order = mref(MakeOrderDto.class, source.getOrder());
        index = source.getIndex();
        part = mref(PartDto.class, source.getPart());
        deadline = source.getDeadline();
        quantity = source.getQuantity();
        price = source.getPrice().toMutable();
        externalProductName = source.getExternalProductName();
        externalSpecification = source.getExternalSpecification();
    }

    @Override
    protected void _unmarshalTo(MakeOrderItem target) {
        merge(target, "order", order);
        target.setIndex(index);
        merge(target, "part", part);
        target.setDeadline(deadline);
        target.setQuantity(quantity);
        target.setPrice(price.toImmutable());
        target.setExternalProductName(externalProductName);
        target.setExternalSpecification(externalSpecification);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    public MakeOrderDto getEnclosingObject() {
        return getOrder();
    }

    @Override
    public void setEnclosingObject(MakeOrderDto enclosingObject) {
        setOrder(enclosingObject);
    }

    public MakeOrderDto getOrder() {
        return order;
    }

    public void setOrder(MakeOrderDto order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        if (part == null)
            throw new NullPointerException("part");
        this.part = part;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
        nativeTotal = null;
    }

    public MutableMCValue getPrice() {
        return price;
    }

    public void setPrice(MutableMCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
        nativePrice = null;
        nativeTotal = null;
    }

    /**
     * 总价=单价*数量。
     */
    public MCValue getTotal() {
        MCValue total = price.multiply(getQuantity());
        return total;
    }

    public BigDecimal getNativePrice()
            throws FxrQueryException {
        if (nativePrice == null) {
            nativePrice = price.getNativeValue(getCreatedDate());
        }
        return nativePrice;
    }

    public BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            BigDecimal price = getNativePrice();
            if (price != null)
                nativeTotal = price.multiply(getQuantity());
        }
        return nativeTotal;
    }

    @NLength(max = MakeOrderItem.EXT_PROD_NAME_LENGTH)
    public String getExternalProductName() {
        return externalProductName;
    }

    public void setExternalProductName(String externalProductName) {
        this.externalProductName = TextUtil.normalizeSpace(externalProductName);
    }

    @NLength(max = MakeOrderItem.EXT_SPEC_LENGTH)
    public String getExternalSpecification() {
        return externalSpecification;
    }


    public void setExternalSpecification(String externalSpecification) {
        this.externalSpecification = TextUtil.normalizeSpace(externalSpecification);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(order), //
                naturalId(part));
    }

}
