package com.bee32.icsf.access.dacl;

import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.dto.AbstractPrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;

public class DACLDto
        extends UIEntityDto<DACL, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int ENTRIES = 1;

    DACLDto parent;
    AbstractPrincipalDto<?> owner;
    List<DACEDto> entries;

    public DACLDto() {
        super();
    }

    public DACLDto(int selection) {
        super(selection);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void _marshal(DACL source) {
        parent = mref(DACLDto.class, source.getParent());
        owner = mref(AbstractPrincipalDto.class, source.getOwner());

        if (selection.contains(ENTRIES))
            entries = marshalList(DACEDto.class, source.getEntries());
    }

    @Override
    protected void _unmarshalTo(DACL target) {
        merge(target, "parent", parent);
        merge(target, "owner", owner);
        mergeList(target, "entries", entries);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public DACLDto getParent() {
        return parent;
    }

    public void setParent(DACLDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    public AbstractPrincipalDto<?> getOwner() {
        return owner;
    }

    public void setOwner(AbstractPrincipalDto<?> owner) {
        if (owner == null)
            throw new NullPointerException("owner");
        this.owner = owner;
    }

    public List<DACEDto> getEntries() {
        return entries;
    }

    public void setEntries(List<DACEDto> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

}
