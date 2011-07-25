package com.bee32.sem.inventory.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.monetary.MCValue;

public class MaterialPriceDto
        extends EntityDto<MaterialPrice, Long> {

    private static final long serialVersionUID = 1L;

    MaterialDto material;
    Date sinceDate;
    MCValue price;
    String comment;

    @Override
    protected void _marshal(MaterialPrice source) {
        this.material = marshal(MaterialDto.class, 0, source.getMaterial());
        this.sinceDate = source.getSinceDate();
        this.price = source.getPrice();
        this.comment = source.getComment();
    }

    @Override
    protected void _unmarshalTo(MaterialPrice target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }

    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
