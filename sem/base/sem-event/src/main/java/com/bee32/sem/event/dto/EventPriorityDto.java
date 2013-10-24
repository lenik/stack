package com.bee32.sem.event.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.DictEntityDto;
import com.bee32.sem.event.entity.EventPriority;

public class EventPriorityDto
        extends DictEntityDto<EventPriority, Integer> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(EventPriority source) {
    }

    @Override
    protected void _unmarshalTo(EventPriority target) {
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
    }

    public int getPriority() {
        return getId_OZ();
    }

    public void setPriority(int priority) {
        setId(priority);
    }

}
