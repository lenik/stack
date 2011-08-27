package com.bee32.sem.event.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.DictEntityDto;
import com.bee32.sem.event.entity.EventPriority;

public class EventPriorityDto
        extends DictEntityDto<EventPriority, Integer> {

    private static final long serialVersionUID = 1L;

    private int priority;

    @Override
    protected void _marshal(EventPriority source) {
        priority = source.getPriority();
    }

    @Override
    protected void _unmarshalTo(EventPriority target) {
        target.setPriority(priority);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        priority = map.getInt("priority");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
