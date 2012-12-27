package com.bee32.sem.material.dto;

import java.io.Serializable;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.material.entity.MaterialAttribute;

public class MaterialAttributeDto
        extends EntityDto<MaterialAttribute, Long>
        implements IEnclosedObject<MaterialDto> {

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
        throw new NotImplementedException();
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
        this.material = material;
    }

    @NLength(min = 2, max = MaterialAttribute.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = TextUtil.normalizeSpace(name);
    }

    @NLength(max = MaterialAttribute.VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = TextUtil.normalizeSpace(value);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(material), name);
    }

}
