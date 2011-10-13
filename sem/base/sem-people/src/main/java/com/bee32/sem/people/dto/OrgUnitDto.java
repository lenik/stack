package com.bee32.sem.people.dto;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.principal.GroupDto;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.people.entity.OrgUnit;

public class OrgUnitDto
        extends TreeEntityDto<OrgUnit, Integer, OrgUnitDto> {

    private static final long serialVersionUID = 1L;

    String name;
    OrgDto org;
    ContactDto contact;
    GroupDto forWhichGroup;

    public OrgUnitDto() {
        super();
    }

    public OrgUnitDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(OrgUnit source) {
        name = source.getName();
        org = mref(OrgDto.class, source.getOrg());
        contact = mref(ContactDto.class, source.getContact());
        forWhichGroup = mref(GroupDto.class, source.getForWhichGroup());
    }

    @Override
    protected void _unmarshalTo(OrgUnit target) {
        target.setName(name);
        merge(target, "org", org);
        merge(target, "contact", contact);
        merge(target, "forWhichGroup", forWhichGroup);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");
        org = new OrgDto().ref(map.getNInt("org"));
        contact = new ContactDto().ref(map.getNInt("contact"));
        forWhichGroup = new GroupDto().ref(map.getInt("forWhichGroup"));
    }

    @NLength(min = 2, max = OrgUnit.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
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

    public ContactDto getContact() {
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
    }

    public GroupDto getForWhichGroup() {
        return forWhichGroup;
    }

    public void setForWhichGroup(GroupDto forWhichGroup) {
        this.forWhichGroup = forWhichGroup;
    }

}
