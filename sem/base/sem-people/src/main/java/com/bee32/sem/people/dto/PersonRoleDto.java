package com.bee32.sem.people.dto;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.people.entity.PersonRole;

public class PersonRoleDto
        extends CEntityDto<PersonRole, Integer>
        implements IEnclosedObject<OrgDto> {

    private static final long serialVersionUID = 1L;

    public static final int ORG_UNIT_FULL = 1;

    public static final int PERSON_CONTACTS = 2;
    public static final int ORG_CONTACTS = 4;

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

    @SuppressWarnings("deprecation")
    @Override
    protected void _marshal(PersonRole source) {
        person = mref(PersonDto.class, //
                selection.translate(PERSON_CONTACTS, PersonDto.CONTACTS), //
                source.getPerson());
        org = mref(OrgDto.class, //
                selection.translate(ORG_CONTACTS, OrgDto.CONTACTS), //
                source.getOrg());
        altOrgUnit = source.getAltOrgUnit();

        orgUnit = mref(OrgUnitDto.class, //
                selection.translate(ORG_UNIT_FULL, OrgUnitDto.PARENT), //
                source.getOrgUnit());

        role = source.getRole();
        roleDetail = source.getRoleDetail();
        description = source.getDescription();
    }

    @SuppressWarnings("deprecation")
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

    @NLength(max = PersonRole.ALT_ORG_UNIT_LENGTH)
    public String getAltOrgUnit() {
        return altOrgUnit;
    }

    public void setAltOrgUnit(String altOrgUnit) {
        this.altOrgUnit = TextUtil.normalizeSpace(altOrgUnit);
    }

    @NLength(max = PersonRole.ROLE_LENGTH)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = TextUtil.normalizeSpace(role);
    }

    @NLength(max = PersonRole.ROLE_DETAIL_LENGTH)
    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = TextUtil.normalizeSpace(roleDetail);
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
        this.description = TextUtil.normalizeSpace(description);
    }

}
