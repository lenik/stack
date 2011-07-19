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
    String description;

    @Override
    protected void _marshal(MaterialPreferredLocation source) {
        this.material = mref(MaterialDto.class, source.getMaterial());
        this.location = mref(StockLocationDto.class, 0, source.getLocation());
        this.description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(MaterialPreferredLocation target) {
        merge(target, "material", material);
        merge(target, "location", location);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        material = new MaterialDto().ref(map.getNLong("material"));
        location = new StockLocationDto().ref(map.getNInt("location"));
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    public StockLocationDto getLocation() {
        return location;
    }

    public void setLocation(StockLocationDto location) {
        if (location == null)
            throw new NullPointerException("location");
        this.location = location;
    }

    @Override
    protected Boolean naturalEquals(EntityDto<MaterialPreferredLocation, Long> other) {
        MaterialPreferredLocationDto o = (MaterialPreferredLocationDto) other;

        if (!material.equals(o.material))
            return false;

        if (!location.equals(o.location))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += material.hashCode();
        hash += location.hashCode();
        return hash;
    }

}
