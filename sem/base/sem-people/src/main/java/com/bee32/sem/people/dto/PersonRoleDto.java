package com.bee32.sem.people.dto;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.people.entity.PersonRole;

public class PersonRoleDto
        extends CEntityDto<PersonRole, Integer>
        implements IEnclosedObject<OrgDto> {

    private static final long serialVersionUID = 1L;

    public static final int ORG_UNIT_FULL = 1;

    public static final int PERSON_CONTACTS = 2;

    OrgDto org;
    PersonDto person;
    OrgUnitDto orgUnit;
    String altOrgUnit;
    String role;
    String roleDetail;
    String description;

    public PersonRoleDto() {
        super();
    }

    public PersonRoleDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(PersonRole source) {
        int x = 0;
        if (selection.contains(PERSON_CONTACTS))
            x |= PersonDto.CONTACTS;
        person = mref(PersonDto.class, x, source.getPerson());

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

    @Override
    public OrgDto getEnclosingObject() {
        return getOrg();
    }

    @Override
    public void setEnclosingObject(OrgDto enclosingObject) {
        setOrg(enclosingObject);
    }

    @NotNull
    public OrgDto getOrg() {
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

    @NotNull
    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public String getAltOrgUnit() {
        return altOrgUnit;
    }

    public void setAltOrgUnit(String altOrgUnit) {
        this.altOrgUnit = altOrgUnit;
    }

    @NLength(max = PersonRole.ROLE_LENGTH)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @NLength(max = PersonRole.ROLE_DETAIL_LENGTH)
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

    @NLength(max = PersonRole.DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
