package com.bee32.sem.inventory.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.inventory.entity.MaterialAttribute;

public class MaterialAttributeDto
        extends CEntityDto<MaterialAttribute, Long> {

    private static final long serialVersionUID = 1L;
    public static final int MATERIAL = 1;

    MaterialDto material;
    String name;
    String value;

    public MaterialAttributeDto() {
    }

    @Override
    protected void _marshal(MaterialAttribute source) {
        if (selection.contains(MATERIAL))
            this.material = mref(MaterialDto.class, source.getMaterial());
        this.name = source.getName();
        this.value = source.getValue();
    }

    @Override
    protected void _unmarshalTo(MaterialAttribute target) {
        merge(target, "material", material);
        target.setName(name);
        target.setValue(value);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(material), name);
    }

}
