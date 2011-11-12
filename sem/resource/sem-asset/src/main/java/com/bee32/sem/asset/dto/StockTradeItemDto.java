package com.bee32.sem.asset.dto;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.StockTradeItem;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

public class StockTradeItemDto
        extends TxEntityDto<StockTradeItem> {

    private static final long serialVersionUID = 1L;

    int index = -1;

    MaterialDto material;

    BigDecimal quantity;
    MCValue price;

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    StockTradeDto trade;

    protected Date getFxrDate() {
        return getCreatedDate();
    }

    @Override
    protected void _marshal(StockTradeItem source) {
        index = source.getIndex();

        material = mref(MaterialDto.class, source.getMaterial());
        price = source.getPrice();

        trade = mref(StockTradeDto.class, source.getTrade());
    }

    @Override
    protected void _unmarshalTo(StockTradeItem target) {
        target.setIndex(index);

        merge(target, "material", material);
        target.setPrice(price);

        merge(target, "trade", trade);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();

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

    public StockTradeDto getTrade() {
        return trade;
    }

    public void setTrade(StockTradeDto trade) {
        this.trade = trade;
    }

}
