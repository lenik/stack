package com.bee32.sem.chance.dto;

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

    WantedProductDto product;
    BigDecimal discountRate = new BigDecimal(1);

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
        this.discountRate = source.getDiscountRate();
    }

    @Override
    protected void _unmarshalTo(WantedProductQuotation target) {
        target.setDiscountRate(discountRate);
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

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    protected Date getFxrDate() {
        return product.getCreatedDate();
    }

}
