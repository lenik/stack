package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.MarshalType;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.PersonRole;

public class PersonRoleDto
        extends EntityDto<PersonRole, Integer> {

    private static final long serialVersionUID = 1L;

    PersonDto person;
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
        person = new PersonDto(0, source.getPerson());
        person.marshalAs(MarshalType.ID_REF);
        org = new OrgDto(0, source.getOrg());
        org.marshalAs(MarshalType.ID_REF);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrgDto getOrg() {
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }
}
