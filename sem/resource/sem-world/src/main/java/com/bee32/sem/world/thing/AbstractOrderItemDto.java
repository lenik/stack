package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MutableMCValue;

public abstract class AbstractOrderItemDto<E extends AbstractOrderItem>
        extends UIEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    int index = -1;

    BigDecimal quantity;
    MutableMCValue price;

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    public AbstractOrderItemDto() {
        super();
    }

    public AbstractOrderItemDto(int fmask) {
        super(fmask);
    }

    @Override
    public AbstractOrderItemDto<E> populate(Object source) {
        if (source instanceof AbstractOrderItemDto) {
            @SuppressWarnings("unchecked")
            AbstractOrderItemDto<E> o = (AbstractOrderItemDto<E>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(AbstractOrderItemDto<E> o) {
        super._populate(o);
        quantity = o.quantity;
        price = o.price.clone();
        nativePrice = o.nativePrice;
        nativeTotal = o.nativeTotal;
    }

    protected boolean isNegated() {
        return false;
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        quantity = source.getQuantity();
        price = source.getPrice().toMutable();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setQuantity(quantity);
        target.setPrice(price);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);

        setQuantity(map.getBigDecimal("quantity"));

        String currencyCode = map.getString("currencyCode");
        Currency currency = Currency.getInstance(currencyCode);
        BigDecimal _price = map.getBigDecimal("price");
        setPrice(new MutableMCValue(currency, _price));
    }

    protected abstract Date getFxrDate();

    /**
     * 单据内部的序号
     */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BigDecimal getQuantity() {
        if (isNegated())
            return quantity.negate();
        else
            return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        if (isNegated())
            this.quantity = quantity.negate();
        else
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

    public Currency getPriceCurrency() {
        return price.getCurrency();
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
            nativePrice = price.getNativeValue(getFxrDate());
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

    boolean important = true; // For quantity = 0

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isDiscardable() {
        return important == false && BigDecimal.ZERO.equals(quantity);
    }

}
