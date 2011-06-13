package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public abstract class SimpleNameDictDto<E extends NameDict>
        extends NameDictDto<E> {

    private static final long serialVersionUID = 1L;

    public SimpleNameDictDto() {
        super();
    }

    public SimpleNameDictDto(E source) {
        super(source);
    }

    @Override
    protected void _marshal(E source) {
    }

    @Override
    protected void _unmarshalTo(E target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
