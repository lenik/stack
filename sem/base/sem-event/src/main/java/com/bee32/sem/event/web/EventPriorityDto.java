package com.bee32.sem.event.web;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.ext.dict.DictDto;
import com.bee32.sem.event.entity.EventPriority;

public class EventPriorityDto
        extends DictDto<EventPriority, Integer> {

    private static final long serialVersionUID = 1L;

    private int priority;

    public EventPriorityDto() {
        super();
    }

    public EventPriorityDto(EventPriority source) {
        super(source);
    }

    @Override
    protected void _marshal(EventPriority source) {
        super._marshal(source);
        priority = source.getPriority();
    }

    @Override
    protected void _unmarshalTo(EventPriority target) {
        super._unmarshalTo(target);
        target.setPriority(priority);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);
        priority = map.getInt("priority");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
