package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.IUnmarshalContext;

public abstract class NameDictDto<E extends NameDict>
        extends DictEntityDto<E, String> {

    private static final long serialVersionUID = 1L;

    String name;

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
        name = source.getName();
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);
        target.setName(name);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);
        name = map.getString("name");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
