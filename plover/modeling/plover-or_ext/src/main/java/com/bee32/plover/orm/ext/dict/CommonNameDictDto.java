package com.bee32.plover.orm.ext.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public class CommonNameDictDto
        extends NameDictDto<NameDict> {

    private static final long serialVersionUID = 1L;

    public CommonNameDictDto() {
        super();
    }

    public CommonNameDictDto(NameDict source) {
        super(source);
    }

    @Override
    protected void _marshal(NameDict source) {
    }

    @Override
    protected void _unmarshalTo(NameDict target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
