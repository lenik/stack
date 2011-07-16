package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.MaterialAttribute;

public class MaterialAttributeDto
        extends EntityDto<MaterialAttribute, Long> {

    private static final long serialVersionUID = 1L;

    private MaterialDto material;
    private String name;
    private String value;

    @Override
    protected void _marshal(MaterialAttribute source) {
        this.material = new MaterialDto().ref(source.getMaterial());
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

}
