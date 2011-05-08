package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public abstract class NumberDictDto<E extends NumberDict>
        extends DictEntityDto<E, Integer> {

    private static final long serialVersionUID = 1L;

    int number;

    public NumberDictDto() {
        super();
    }

    public NumberDictDto(E source) {
        super(source);
    }

    public NumberDictDto(int selection) {
        super(selection);
    }

    public NumberDictDto(int selection, E source) {
        super(selection, source);
    }

    @Override
    protected final void __marshal(E source) {
        super.__marshal(source);
        number = source.getNumber();
    }

    @Override
    protected final void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setNumber(number);
    }

    @Override
    protected final void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);
        number = map.getInt("number");
    }

}
