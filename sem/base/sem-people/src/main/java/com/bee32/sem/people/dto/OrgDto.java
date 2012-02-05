package com.bee32.sem.people.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.ParseException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PersonRole;

public class OrgDto
        extends PartyDto {

    private static final long serialVersionUID = 1L;

    OrgTypeDto type;
    int size;

    Set<PersonRoleDto> roles;
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

        if (selection.contains(ROLES)) {
            roles = new HashSet<PersonRoleDto>();
            for (PersonRole role : source.getRoles()) {
                PersonRoleDto roleDto = marshal(PersonRoleDto.class, role);
                roles.add(roleDto);
            }
        }

        forWhichGroup = mref(GroupDto.class, source.getForWhichGroup());
    }

    @Override
    protected void _unmarshalTo(Party _target) {
        super._unmarshalTo(_target);

        Org target = (Org) _target;

        merge(target, "type", type);
        target.setSize(size);

        mergeSet(target, "roles", roles);

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

    @Min(1)
    @Max(10000)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Set<PersonRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRoleDto> roles) {
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
