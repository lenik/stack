package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;

public class MaterialPreferredLocationDto
        extends EntityDto<MaterialPreferredLocation, Long> {

    private static final long serialVersionUID = 1L;

    MaterialDto material;
    StockLocationDto location;

    @Override
    protected void _marshal(MaterialPreferredLocation source) {
        this.material = mref(MaterialDto.class, source.getMaterial());
        this.location = mref(StockLocationDto.class, 0, source.getLocation());
    }

    @Override
    protected void _unmarshalTo(MaterialPreferredLocation target) {
        merge(target, "material", material);
        merge(target, "location", location);
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

    public StockLocationDto getLocation() {
        return location;
    }

    public void setLocation(StockLocationDto location) {
        this.location = location;
    }

}
