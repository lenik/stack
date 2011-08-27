package com.bee32.plover.ox1.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

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

    public String getDisplayId() {
        if (id == null)
            return "";
        else
            return id;
    }

    public void setDisplayId(String displayId) {
        if (displayId != null && displayId.isEmpty())
            id = null;
        else
            id = displayId;
    }

    // Using the default id-equality.
    //
    // protected Boolean naturalEquals(EntityDto<E, String> other);
    // protected Integer naturalHashCode();

}
