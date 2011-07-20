package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;

public class MaterialPreferredLocationDto
        extends UIEntityDto<MaterialPreferredLocation, Long> {

    private static final long serialVersionUID = 1L;

    MaterialDto material;
    StockLocationDto location;

    boolean permanent;

    @Override
    protected void _marshal(MaterialPreferredLocation source) {
        material = mref(MaterialDto.class, source.getMaterial());
        location = mref(StockLocationDto.class, source.getLocation());
        permanent = source.isPermanent();
    }

    @Override
    protected void _unmarshalTo(MaterialPreferredLocation target) {
        merge(target, "material", material);
        merge(target, "location", location);
        target.setPermanent(permanent);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        material = new MaterialDto().ref(map.getNLong("material"));
        location = new StockLocationDto().ref(map.getNInt("location"));
        permanent = map.getBoolean("permanent");
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

        if (material == null || location == null)
            return false;

        if (!material.equals(o.material))
            return false;

        if (!location.equals(o.location))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;

        if (material == null || location == null)
            return System.identityHashCode(this);

        hash += material.hashCode();
        hash += location.hashCode();
        return hash;
    }

}
