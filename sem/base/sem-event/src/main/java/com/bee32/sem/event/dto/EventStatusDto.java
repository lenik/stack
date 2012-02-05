package com.bee32.sem.event.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.DictEntityDto;
import com.bee32.sem.event.entity.EventStatus;

public class EventStatusDto
        extends DictEntityDto<EventStatus, Integer> {

    private static final long serialVersionUID = 1L;

    private int flagsMask;
    private boolean closed;
    private int state;

    public EventStatusDto() {
        super();
    }

    public EventStatusDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(EventStatus source) {
        flagsMask = source.getFlagsMask();
        closed = source.isClosed();
        state = source.getState();
    }

    @Override
    protected void _unmarshalTo(EventStatus target) {
        target.setFlagsMask(flagsMask);
        target.setClosed(closed);
        target.setState(state);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        flagsMask = map.getInt("flagsMask");
        closed = map.getBoolean("closed");
        state = map.getInt("state");
    }

}
