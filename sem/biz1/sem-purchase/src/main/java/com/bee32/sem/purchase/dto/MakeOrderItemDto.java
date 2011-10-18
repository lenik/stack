package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.purchase.entity.MakeOrderItem;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sems.bom.dto.PartDto;

public class MakeOrderItemDto
        extends CEntityDto<MakeOrderItem, Long> {

    private static final long serialVersionUID = 1L;

    MakeOrderDto order;
    int index;
    PartDto part;
    BigDecimal quantity = new BigDecimal(1);
    MCValue price;

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    String externalProductName;
    String externalSpecification;

    @Override
    protected void _marshal(MakeOrderItem source) {
        order = mref(MakeOrderDto.class, source.getOrder());
        index = source.getIndex();
        part = mref(PartDto.class, source.getPart());
        quantity = source.getQuantity();
        price = source.getPrice();
        externalProductName = source.getExternalProductName();
        externalSpecification = source.getExternalSpecification();
    }

    @Override
    protected void _unmarshalTo(MakeOrderItem target) {
        merge(target, "order", order);
        target.setIndex(index);
        merge(target, "part", part);
        target.setQuantity(quantity);
        target.setPrice(price);
        target.setExternalProductName(externalProductName);
        target.setExternalSpecification(externalSpecification);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
        nativeTotal = null;
    }

    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
        nativePrice = null;
        nativeTotal = null;
    }

    public Currency getPriceCurrency() {
        return price.getCurrency();
    }

    public String getPriceCurrencyCode() {
        return price.getCurrencyCode();
    }

    public void setPriceCurrencyCode(String currencyCode) {
        if (currencyCode == null)
            throw new NullPointerException("currencyCode");
        Currency currency = Currency.getInstance(currencyCode);
        setPrice(new MCValue(currency, price.getValue()));
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public void setPriceValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        setPrice(new MCValue(price.getCurrency(), value));
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

    public String getExternalProductName() {
        return externalProductName;
    }

    public void setExternalProductName(String externalProductName) {
        this.externalProductName = externalProductName;
    }

    public String getExternalSpecification() {
        return externalSpecification;
    }

    public void setExternalSpecification(String externalSpecification) {
        this.externalSpecification = externalSpecification;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(order), //
                naturalId(part));
    }

}
