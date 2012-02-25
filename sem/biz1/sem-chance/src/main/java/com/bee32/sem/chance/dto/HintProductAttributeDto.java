package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.HintProductAttribute;
import com.bee32.sem.frame.ui.IEnclosedObject;

public class HintProductAttributeDto
    extends EntityDto<HintProductAttribute, Long>
    implements IEnclosedObject<HintProductDto> {

    private static final long serialVersionUID = 1L;

    public static final int HINT_PRODUCT = 1;

    HintProductDto product;

    String name;
    String value;

    public HintProductAttributeDto() {

    }

    @Override
    public HintProductDto getEnclosingObject() {
        return getProduct();
    }

    @Override
    public void setEnclosingObject(HintProductDto enclosingObject) {
        setProduct(enclosingObject);
    }

    @Override
    protected void _marshal(HintProductAttribute source) {
        if(selection.contains(HINT_PRODUCT))
            this.product = mref(HintProductDto.class, source.getProduct());

        this.name = source.getName();
        this.value = source.getValue();
    }

    @Override
    protected void _unmarshalTo(HintProductAttribute target) {
        merge(target, "product", product);
        target.setName(name);
        target.setValue(value);

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

    @NLength(min = 1, max = HintProductAttribute.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NLength(min = 1, max = HintProductAttribute.VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
