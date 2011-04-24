package com.bee32.sem.event.web;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.ext.dict.DictEntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
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

    public EventStatusDto(EventStatus source) {
        super(source);
    }

    @Override
    protected void _marshal(EventStatus source) {
        super._marshal(source);
        flagsMask = source.getFlagsMask();
        closed = source.isClosed();
        state = source.getState();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, EventStatus target) {
        super._unmarshalTo(context, target);
        target.setFlagsMask(flagsMask);
        target.setClosed(closed);
        target.setState(state);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);
        flagsMask = map.getInt("flagsMask");
        closed = map.getBoolean("closed");
        state = map.getInt("state");
    }

}
