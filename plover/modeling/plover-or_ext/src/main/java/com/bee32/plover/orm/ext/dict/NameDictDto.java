package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public abstract class NameDictDto<E extends NameDict>
        extends DictEntityDto<E, String> {

    private static final long serialVersionUID = 1L;

    public NameDictDto() {
        super();
    }

    public NameDictDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);

        // name will always overwrite the id here.
        setName(map.getString("name"));
    }

    public String getName() {
        return getId();
    }

    public void setName(String name) {
        setId(name);
    }

    @Override
    protected Boolean naturalEquals(EntityDto<E, String> other) {
        NameDictDto<E> o = (NameDictDto<E>) other;

        String name = getName();
        String otherName = o.getName();
        if (name == null || otherName == null)
            return false;

        if (!name.equals(otherName))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        String name = getName();

        if (name == null)
            return 0;
        else
            return name.hashCode();
    }

}
