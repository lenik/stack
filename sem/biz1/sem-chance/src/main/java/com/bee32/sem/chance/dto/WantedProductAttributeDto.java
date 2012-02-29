package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.WantedProductAttribute;
import com.bee32.sem.frame.ui.IEnclosedObject;

public class WantedProductAttributeDto
        extends EntityDto<WantedProductAttribute, Long>
        implements IEnclosedObject<WantedProductDto> {

    private static final long serialVersionUID = 1L;

    public static final int HINT_PRODUCT = 1;

    WantedProductDto product;

    String name;
    String value;

    public WantedProductAttributeDto() {
    }

    public WantedProductAttributeDto(int fmask) {
        super(fmask);
    }

    @Override
    public WantedProductDto getEnclosingObject() {
        return getProduct();
    }

    @Override
    public void setEnclosingObject(WantedProductDto enclosingObject) {
        setProduct(enclosingObject);
    }

    @Override
    protected void _marshal(WantedProductAttribute source) {
        if (selection.contains(HINT_PRODUCT))
            this.product = mref(WantedProductDto.class, source.getProduct());

        this.name = source.getName();
        this.value = source.getValue();
    }

    @Override
    protected void _unmarshalTo(WantedProductAttribute target) {
        target.setName(name);
        target.setValue(value);
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

    @NLength(min = 1, max = WantedProductAttribute.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NLength(max = WantedProductAttribute.VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
