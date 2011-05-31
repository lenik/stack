package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.Org;

public class OrgDto
        extends EntityDto<Org, Integer> {

    private static final long serialVersionUID = 1L;

    public OrgDto() {
        super();
    }

    public OrgDto(Org source) {
        super(source);
    }

    @Override
    protected void _marshal(Org source) {
    }

    @Override
    protected void _unmarshalTo(Org target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
