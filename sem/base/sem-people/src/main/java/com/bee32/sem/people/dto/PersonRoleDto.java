package com.bee32.sem.people.dto;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.people.entity.PersonRole;

public class PersonRoleDto
        extends CEntityDto<PersonRole, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int ORG_UNIT_FULL = 1;

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

        int orgUnitSelection = 0;
        if (selection.contains(ORG_UNIT_FULL))
            orgUnitSelection |= OrgUnitDto.PARENT;
        orgUnit = mref(OrgUnitDto.class, orgUnitSelection, source.getOrgUnit());

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

    @NotNull
    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    @NotNull
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

    @Size(max = PersonRole.ROLE_LENGTH)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Size(max = PersonRole.ROLE_DETAIL_LENGTH)
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

    @Size(max = PersonRole.DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
