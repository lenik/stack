package com.bee32.plover.orm.util.hello.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.hello.entity.HelloMessage;

public class HelloMessageDto
        extends EntityDto<HelloMessage, Integer> {

    private static final long serialVersionUID = 1L;

    String message;

    public HelloMessageDto() {
        super();
    }

    public HelloMessageDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(HelloMessage source) {
        this.message = source.getMessage();
    }

    @Override
    protected void _unmarshalTo(HelloMessage target) {
        target.setMessage(message);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        message = map.getString("message");
    }

}
