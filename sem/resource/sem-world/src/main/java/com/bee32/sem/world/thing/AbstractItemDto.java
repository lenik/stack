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

public abstract class AbstractItemDto<E extends AbstractItem>
        extends UIEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    int index = -1;

    BigDecimal quantity;
    MutableMCValue price;

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    public AbstractItemDto() {
        super();
    }

    public AbstractItemDto(int fmask) {
        super(fmask);
    }

    @Override
    public AbstractItemDto<E> populate(Object source) {
        if (source instanceof AbstractItemDto) {
            @SuppressWarnings("unchecked")
            AbstractItemDto<E> o = (AbstractItemDto<E>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(AbstractItemDto<E> o) {
        super._populate(o);
        setQuantity(o.quantity);
        price = o.price.clone();
        nativePrice = o.nativePrice;
        nativeTotal = o.nativeTotal;
    }

    protected boolean isNegated() {
        return false;
    }

    protected boolean isQuantityOptional() {
        return false;
    }

    public boolean isDiscardable() {
        return get_checked() == Boolean.FALSE;
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        setQuantity(source.getQuantity());
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
        if (quantity == null)
            return null;
        if (isNegated())
            return quantity.negate();
        else
            return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null) {
            if (!isQuantityOptional())
                throw new NullPointerException("quantity");
        } else if (isNegated()) {
            quantity = quantity.negate();
        }
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
        if (getQuantity() == null)
            return null;
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
            if (getQuantity() == null)
                return null;
            BigDecimal price = getNativePrice();
            if (price != null)
                nativeTotal = price.multiply(getQuantity());
        }
        return nativeTotal;
    }

}
