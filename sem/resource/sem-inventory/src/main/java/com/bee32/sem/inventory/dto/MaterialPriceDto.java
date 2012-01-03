package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.monetary.MCValue;

public class MaterialPriceDto
        extends UIEntityDto<MaterialPrice, Long> {

    private static final long serialVersionUID = 1L;

    MaterialDto material;
    Date date;
    MCValue price;

    @Override
    protected void _marshal(MaterialPrice source) {
        this.material = marshal(MaterialDto.class, 0, source.getMaterial());
        this.date = source.getDate();
        this.price = source.getPrice();
    }

    @Override
    protected void _unmarshalTo(MaterialPrice target) {
        merge(target, "material", material);
        target.setDate(date);
        target.setPrice(price.clone());
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null)
            throw new NullPointerException("date");
        this.date = date;
// this.date = LocalDateUtil.truncate(date);
    }

    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

    /**
     * Set price in native currency.
     */
    public final void setPrice(double price) {
        setPrice(new MCValue(CurrencyConfig.getNative(), price));
    }

    public double getViewPrice() {
        return price.getValue().doubleValue();
    }

    public void setViewPrice(double viewPrice) {
        price = price.value(viewPrice);
    }

    public BigDecimal getPriceDigit() {
        if (price == null)
            return new BigDecimal(0);
        return price.getValue();
    }

    public void setPriceDigit(BigDecimal priceDigit) {
        Currency c = CurrencyConfig.getNative();
        if (price != null)
            c = price.getCurrency();

        price = new MCValue(c, priceDigit);
    }

    public String getPriceCurrency() {
        if (price == null)
            return CurrencyConfig.getNative().getCurrencyCode();

        if (price.getCurrency() == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return price.getCurrency().getCurrencyCode();
    }

    public void setPriceCurrency(String currencyCode) {
        if (price == null)
            price = new MCValue(Currency.getInstance(currencyCode), 0);
        else
            price = new MCValue(Currency.getInstance(currencyCode), price.getValue());
    }

}
