package com.bee32.sem.event.dto;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.ext.dict.DictEntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.event.entity.EventPriority;

public class EventPriorityDto
        extends DictEntityDto<EventPriority, Integer> {

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
    protected void _unmarshalTo(IUnmarshalContext context, EventPriority target) {
        super._unmarshalTo(context, target);
        target.setPriority(priority);
    }

    @Override
    public void _parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super._parse(map);
        priority = map.getInt("priority");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
