package com.bee32.plover.ox1.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public abstract class LongNumberDictDto<E extends LongNumberDict>
        extends DictEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    long number;

    public LongNumberDictDto() {
        super();
    }

    public LongNumberDictDto(int fmask) {
        super(fmask);
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
