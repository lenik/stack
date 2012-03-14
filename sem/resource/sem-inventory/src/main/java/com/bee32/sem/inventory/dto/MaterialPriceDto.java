package com.bee32.sem.inventory.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.monetary.MutableMCValue;

public class MaterialPriceDto
        extends UIEntityDto<MaterialPrice, Long>
        implements IEnclosedObject<MaterialDto> {

    private static final long serialVersionUID = 1L;

    MaterialDto material;
    Date date;
    MutableMCValue price;

    @Override
    protected void _marshal(MaterialPrice source) {
        this.material = marshal(MaterialDto.class, 0, source.getMaterial());
        this.date = source.getDate();
        this.price = source.getPrice().toMutable();
    }

    @Override
    protected void _unmarshalTo(MaterialPrice target) {
        merge(target, "material", material);
        target.setDate(date);
        target.setPrice(price.toImmutable());
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    public MaterialDto getEnclosingObject() {
        return getMaterial();
    }

    @Override
    public void setEnclosingObject(MaterialDto enclosingObject) {
        setMaterial(enclosingObject);
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

    public MutableMCValue getPrice() {
        return price;
    }

    public void setPrice(MutableMCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

}
