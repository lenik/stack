package com.bee32.sem.people.dto;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.people.entity.OrgUnit;

public class OrgUnitDto
        extends TreeEntityDto<OrgUnit, Integer, OrgUnitDto>
        implements IEnclosedObject<OrgDto> {

    private static final long serialVersionUID = 1L;

    public static final int ROLES = 1;

    OrgDto org;
    ContactDto contact;
    List<PersonRoleDto> roles;
    GroupDto forWhichGroup;

    public OrgUnitDto() {
        super();
    }

    public OrgUnitDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(OrgUnit source) {
        org = mref(OrgDto.class, source.getOrg());
        contact = mref(ContactDto.class, source.getContact());

        if (selection.contains(ROLES))
            roles = mrefList(PersonRoleDto.class, source.getRoles());
        else
            roles = Collections.emptyList();

        forWhichGroup = mref(GroupDto.class, source.getForWhichGroup());
    }

    @Override
    protected void _unmarshalTo(OrgUnit target) {
        merge(target, "org", org);
        merge(target, "contact", contact);

        if (selection.contains(ROLES))
            mergeList(target, "roles", roles);

        merge(target, "forWhichGroup", forWhichGroup);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        org = new OrgDto().ref(map.getNInt("org"));
        contact = new ContactDto().ref(map.getNInt("contact"));
        forWhichGroup = new GroupDto().ref(map.getInt("forWhichGroup"));
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
        if (org == null)
            throw new NullPointerException("org");
        this.org = org;
    }

    @NLength(min = 2, max = OrgUnit.LABEL_LENGTH)
    @Override
    public String getLabel() {
        return super.getLabel();
    }

    @NLength(min = 2, max = OrgUnit.LABEL_LENGTH)
    @Deprecated
    @Override
    public String getName() {
        return super.getLabel();
    }

    @Override
    protected void setName(String name) {
        setLabel(name);
    }

    public ContactDto getContact() {
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
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

}
