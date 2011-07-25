package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.Currency;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.MarshalException;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

public abstract class AbstractOrderItemDto<E extends AbstractOrderItem>
        extends UIEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    BigDecimal quantity;

    MCValue price = new MCValue();

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        quantity = source.getQuantity();
        price = new MCValue(source.getPrice());
        try {
            nativePrice = source.getNativePrice();
            nativeTotal = source.getNativeTotal();
        } catch (FxrQueryException e) {
            throw new MarshalException(e.getMessage(), e);
        }
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

        quantity = map.getBigDecimal("quantity");

        String currencyCode = map.getString("currencyCode");
        Currency currency = Currency.getInstance(currencyCode);
        BigDecimal _price = map.getBigDecimal("price");
        price = new MCValue(currency, _price);
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
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
        price = new MCValue(currency, price.getValue());
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public void setPriceValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        price = new MCValue(price.getCurrency(), value);
    }

    public BigDecimal getNativePrice() {
        return nativePrice;
    }

    public BigDecimal getNativeTotal() {
        return nativeTotal;
    }

}
