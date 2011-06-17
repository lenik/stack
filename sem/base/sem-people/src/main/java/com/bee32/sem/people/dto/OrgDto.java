package com.bee32.sem.people.dto;

import java.util.HashSet;
import java.util.Set;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.PersonRole;

public class OrgDto
        extends AbstractPartyDto<Org> {

    private static final long serialVersionUID = 1L;

    OrgTypeDto type;
    int size;

    Set<PersonRoleDto> roles;

    public OrgDto() {
        super();
    }

    public OrgDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Org source) {
        super._marshal(source);

        type = marshal(OrgTypeDto.class, source.getType(), true);
        size = source.getSize();

        if (selection.contains(ROLES)) {
            roles = new HashSet<PersonRoleDto>();
            for (PersonRole role : source.getRoles()) {
                PersonRoleDto roleDto = new PersonRoleDto(role);
                roles.add(roleDto);
            }
        }
    }

    @Override
    protected void _unmarshalTo(Org target) {
        super._unmarshalTo(target);

        merge(target, "type", type);
        target.setSize(size);

        mergeSet(target, "roles", roles);
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
        this.roles = roles;
    }
}
