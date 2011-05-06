package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.people.entity.Organization;

public class OrganizationDto
        extends EntityDto<Organization, Integer> {

    private static final long serialVersionUID = 1L;

    public OrganizationDto() {
        super();
    }

    public OrganizationDto(Organization source) {
        super(source);
    }

    @Override
    protected void _marshal(Organization source) {
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Organization target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
