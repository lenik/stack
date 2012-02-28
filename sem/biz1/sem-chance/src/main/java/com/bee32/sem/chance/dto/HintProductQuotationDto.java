package com.bee32.sem.chance.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.chance.entity.HintProductQuotation;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.world.thing.AbstractItemDto;

public class HintProductQuotationDto
    extends AbstractItemDto<HintProductQuotation>
    implements IEnclosedObject<HintProductDto> {

    private static final long serialVersionUID = 1L;

    public static final int HINT_PRODUCT = 1;

    HintProductDto product;
    BigDecimal discountRate = new BigDecimal(1);

    @Override
    public HintProductDto getEnclosingObject() {
        return getProduct();
    }

    @Override
    public void setEnclosingObject(HintProductDto enclosingObject) {
        setProduct(enclosingObject);

    }

    @Override
    protected void _marshal(HintProductQuotation source) {
        if(selection.contains(HINT_PRODUCT))
            this.product = mref(HintProductDto.class, source.getProduct());

        this.discountRate = source.getDiscountRate();
    }

    @Override
    protected void _unmarshalTo(HintProductQuotation target) {
        target.setDiscountRate(discountRate);
        merge(target, "product", product);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public HintProductDto getProduct() {
        return product;
    }

    public void setProduct(HintProductDto product) {
        if (product == null)
            throw new NullPointerException("product");
        this.product = product;
    }

    @Override
    protected Date getFxrDate() {
        return product.getCreatedDate();
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
}
