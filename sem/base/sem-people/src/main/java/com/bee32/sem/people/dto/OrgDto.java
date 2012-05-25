package com.bee32.sem.people.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.Party;

public class OrgDto
        extends PartyDto {

    private static final long serialVersionUID = 1L;

    public static final int ORG_UNITS = 0x100;

    OrgTypeDto type;
    int size;

    List<OrgUnitDto> orgUnits;
    List<PersonRoleDto> roles;

    GroupDto forWhichGroup;

    public OrgDto() {
        super();
    }

    public OrgDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Party _source) {
        super._marshal(_source);

        Org source = (Org) _source;

        type = marshal(OrgTypeDto.class, source.getType(), true);
        size = source.getSize();

        if (selection.contains(ROLES))
            roles = marshalList(PersonRoleDto.class, source.getRoles());
        else
            roles = Collections.emptyList();

        if (selection.contains(ORG_UNITS))
            orgUnits = marshalList(OrgUnitDto.class, source.getOrgUnits());
        else
            orgUnits = Collections.emptyList();

        forWhichGroup = mref(GroupDto.class, source.getForWhichGroup());
    }

    @Override
    protected void _unmarshalTo(Party _target) {
        super._unmarshalTo(_target);

        Org target = (Org) _target;

        merge(target, "type", type);
        target.setSize(size);

        if (selection.contains(ROLES))
            mergeSet(target, "roles", roles);

        if (selection.contains(ORG_UNITS))
            mergeList(target, "orgUnits", orgUnits);

        merge(target, "forWhichGroup", forWhichGroup);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public OrgTypeDto getType() {
        return type;
    }

    public void setType(OrgTypeDto type) {
        this.type = type;
    }

    @Min(0)
    @Max(10000)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<OrgUnitDto> getOrgUnits() {
        return orgUnits;
    }

    public void setOrgUnits(List<OrgUnitDto> orgUnits) {
        if (orgUnits == null)
            throw new NullPointerException("orgUnits");
        this.orgUnits = orgUnits;
    }

    public List<PersonRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<PersonRoleDto> roles) {
        if (roles == null)
            throw new NullPointerException("roles");
        this.roles = roles;
    }

    public GroupDto getForWhichGroup() {
        return forWhichGroup;
    }

    public void setForWhichGroup(GroupDto forWhichGroup) {
        this.forWhichGroup = forWhichGroup;
    }

    public List<PersonRoleDto> getRoleList() {
        List<PersonRoleDto> roleList = new ArrayList<PersonRoleDto>();

        roleList.addAll(roles);
        return roleList;
    }

}
