package com.bee32.icsf.access.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.tree.TreeEntityDto;

public class ACLDto
        extends TreeEntityDto<ACL, Integer, ACLDto> {

    private static final long serialVersionUID = 1L;

    public static final int INHERITED_ACL = PARENT;
    public static final int ENTRIES = 1;

    String name;
    List<ACLEntryDto> entries;

    public ACLDto() {
        super();
    }

    public ACLDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(ACL source) {
        name = source.getName();
        if (selection.contains(ENTRIES))
            entries = marshalList(ACLEntryDto.class, source.getEntries());
        else
            entries = new ArrayList<ACLEntryDto>();
    }

    @Override
    protected void _unmarshalTo(ACL target) {
        target.setName(name);
        if (selection.contains(ENTRIES)) {
            mergeList(target, "entries", entries);
            target.compactPermission();
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public ACLDto getInheritedACL() {
        return getParent();
    }

    public void setInheritedACL(ACLDto inheritedDto) {
        if (inheritedDto == null)
            inheritedDto = new ACLDto().ref();
        setParent(inheritedDto);
    }

    @NLength(max = ACL.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ACLEntryDto> getEntries() {
        return entries;
    }

    public SelectableList<ACLEntryDto> getSelectableEntries() {
        return SelectableList.decorate(entries);
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        return name;
    }

}
