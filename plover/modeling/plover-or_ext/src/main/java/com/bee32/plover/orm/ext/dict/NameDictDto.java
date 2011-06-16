package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public abstract class NameDictDto<E extends NameDict>
        extends DictEntityDto<E, String> {

    private static final long serialVersionUID = 1L;

    public NameDictDto() {
        super();
    }

    public NameDictDto(E source) {
        super(source);
    }

    public NameDictDto(int selection) {
        super(selection);
    }

    public NameDictDto(int selection, E source) {
        super(selection, source);
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

}
