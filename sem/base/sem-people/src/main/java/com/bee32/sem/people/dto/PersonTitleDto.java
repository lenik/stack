package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.PersonTitle;

public class PersonTitleDto
        extends EntityDto<PersonTitle, Integer> {

    private static final long serialVersionUID = 1L;

    PersonDto person;
    OrganizationDto org;
    String orgUnit;
    String job;
    String role;
    String description;

    public PersonTitleDto() {
        super();
    }

    public PersonTitleDto(PersonTitle source) {
        super(source);
    }

    @Override
    protected void _marshal(PersonTitle source) {
        person = new PersonDto(source.getPerson());
        org = new OrganizationDto(source.getOrg());
        orgUnit = source.getOrgUnit();
        job = source.getJob();
        role = source.getRole();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PersonTitle target) {
        merge(target, "person", person);
        merge(target, "org", org);

        target.setOrgUnit(orgUnit);
        target.setJob(job);
        target.setRole(role);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        orgUnit = map.getString("orgUnit");
        job = map.getString("job");
        role = map.getString("role");
        description = map.getString("description");
    }

}
