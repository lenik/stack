package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.IUnmarshalContext;

public abstract class LongNumberDictDto<E extends LongNumberDict>
        extends DictEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    long number;

    public LongNumberDictDto() {
        super();
    }

    public LongNumberDictDto(E source) {
        super(source);
    }

    public LongNumberDictDto(int selection) {
        super(selection);
    }

    public LongNumberDictDto(int selection, E source) {
        super(selection, source);
    }

    @Override
    protected final void __marshal(E source) {
        super.__marshal(source);
        number = source.getNumber();
    }

    @Override
    protected final void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);
        target.setNumber(number);
    }

    @Override
    protected final void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);
        number = map.getInt("number");
    }

}
