package com.bee32.sem.inventory.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.monetary.CurrencyConfig;
import com.bee32.sem.world.monetary.MCValue;

public class MaterialPriceDto
        extends UIEntityDto<MaterialPrice, Long> {

    private static final long serialVersionUID = 1L;

    MaterialDto material;
    Date date;
    MCValue price;
    double viewPrice;

    @Override
    protected void _marshal(MaterialPrice source) {
        this.material = marshal(MaterialDto.class, 0, source.getMaterial());
        this.date = source.getDate();
        this.price = source.getPrice();
        this.viewPrice = price.getValue().doubleValue();
    }

    @Override
    protected void _unmarshalTo(MaterialPrice target) {
        merge(target, "material", material);
        target.setDate(date);
        MCValue _mprice = new MCValue(price);
        _mprice.value(viewPrice);
        target.setPrice(new MCValue(_mprice));
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

    public double getViewPrice() {
        return viewPrice;
    }

    public void setViewPrice(double viewPrice) {
        this.viewPrice = viewPrice;
    }

    /**
     * Set price in native currency.
     */
    public final void setPrice(double price) {
        setPrice(new MCValue(CurrencyConfig.getNative(), price));
    }

}
