package com.bee32.sem.chance.dto;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.chance.entity.WantedProductQuotation;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.world.thing.AbstractItemDto;

public class WantedProductQuotationDto
        extends AbstractItemDto<WantedProductQuotation>
        implements IEnclosedObject<WantedProductDto> {

    private static final long serialVersionUID = 1L;

    public static final int DISCOUNT_SCALE = WantedProductQuotation.DISCOUNT_SCALE;
    static final BigDecimal DISCOUNT_SCALE_REAL = new BigDecimal(DISCOUNT_SCALE);

    WantedProductDto product;
    int discount;

    @Override
    public WantedProductDto getEnclosingObject() {
        return getProduct();
    }

    @Override
    public void setEnclosingObject(WantedProductDto enclosingObject) {
        setProduct(enclosingObject);

    }

    @Override
    protected void _marshal(WantedProductQuotation source) {
        this.product = mref(WantedProductDto.class, source.getProduct());
        this.discount = source.getDiscount();
    }

    @Override
    protected void _unmarshalTo(WantedProductQuotation target) {
        target.setDiscount(discount);
        merge(target, "product", product);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();

    }

    public WantedProductDto getProduct() {
        return product;
    }

    public void setProduct(WantedProductDto product) {
        if (product == null)
            throw new NullPointerException("product");
        this.product = product;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Transient
    public BigDecimal getDiscountReal() {
        BigDecimal discountReal = new BigDecimal(discount).divide(DISCOUNT_SCALE_REAL);
        return discountReal;
    }

    @Transient
    public int getDiscountPercent() {
        return discount * 100 / DISCOUNT_SCALE;
    }

    public void setDiscountPercent(int discountPercent) {
        discount = discountPercent * DISCOUNT_SCALE / 100;
    }

    @Override
    protected Date getFxrDate() {
        return product.getCreatedDate();
    }

}
