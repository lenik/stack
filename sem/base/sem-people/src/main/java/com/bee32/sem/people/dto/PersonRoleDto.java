package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.CEntityDto;
import com.bee32.sem.people.entity.PersonRole;

public class PersonRoleDto
        extends CEntityDto<PersonRole, Integer> {

    private static final long serialVersionUID = 1L;

    PersonDto person;
    OrgDto org;
    OrgUnitDto orgUnit;
    String altOrgUnit;
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
        person = mref(PersonDto.class, source.getPerson());
        org = mref(OrgDto.class, source.getOrg());
        altOrgUnit = source.getAltOrgUnit();
        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());
        role = source.getRole();
        roleDetail = source.getRoleDetail();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PersonRole target) {
        merge(target, "person", person);
        merge(target, "org", org);
        merge(target, "orgUnit", orgUnit);
        target.setAltOrgUnit(altOrgUnit);
        target.setRole(role);
        target.setRoleDetail(roleDetail);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        altOrgUnit = map.getString("altOrgUnit");
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

    public String getAltOrgUnit() {
        return altOrgUnit;
    }

    public void setAltOrgUnit(String altOrgUnit) {
        this.altOrgUnit = altOrgUnit;
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

    public OrgUnitDto getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDto orgUnit) {
        this.orgUnit = orgUnit;
    }

}
