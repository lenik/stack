package com.bee32.sem.inventory.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;

public class MaterialPreferredLocationDto
        extends UIEntityDto<MaterialPreferredLocation, Long>
        implements IEnclosedObject<MaterialDto> {

    private static final long serialVersionUID = 1L;
    public static final int MATERIAL = 1;

    MaterialDto material;
    StockLocationDto location;

    boolean permanent;

    @Override
    protected void _marshal(MaterialPreferredLocation source) {
        if (selection.contains(MATERIAL))
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

    public StockLocationDto getLocation() {
        return location;
    }

    public void setLocation(StockLocationDto location) {
        if (location == null)
            throw new NullPointerException("location");
        this.location = location;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(material), naturalId(location));
    }

}
