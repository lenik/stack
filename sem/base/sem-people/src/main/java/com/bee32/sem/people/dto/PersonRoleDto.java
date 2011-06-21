package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.PersonRole;

public class PersonRoleDto
        extends EntityDto<PersonRole, Integer> {

    private static final long serialVersionUID = 1L;

    PartyDto person;
    OrgDto org;
    String orgUnit;
    String role;
    String roleDetail;
    String description;

    public PersonRoleDto() {
        super();
    }

    public PersonRoleDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(PersonRole source) {
        person = new PartyDto(0).ref(source.getPerson());
        org = new OrgDto().ref(source.getOrg());
        orgUnit = source.getOrgUnit();
        role = source.getRole();
        roleDetail = source.getRoleDetail();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PersonRole target) {
        merge(target, "person", person);
        merge(target, "org", org);

        target.setOrgUnit(orgUnit);
        target.setRole(role);
        target.setRoleDetail(roleDetail);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        orgUnit = map.getString("orgUnit");
        role = map.getString("role");
        roleDetail = map.getString("roleDetail");
        description = map.getString("description");
    }

}
