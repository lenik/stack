package com.bee32.icsf.access.dacl;

import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;

public class DACLDto
        extends TreeEntityDto<DACL, Integer, DACLDto> {

    private static final long serialVersionUID = 1L;

    public static final int ENTRIES = 1;

    List<DACEDto> entries;

    public DACLDto() {
        super();
    }

    public DACLDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(DACL source) {
        if (selection.contains(ENTRIES))
            entries = marshalList(DACEDto.class, source.getEntries());
    }

    @Override
    protected void _unmarshalTo(DACL target) {
        mergeList(target, "entries", entries);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
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
