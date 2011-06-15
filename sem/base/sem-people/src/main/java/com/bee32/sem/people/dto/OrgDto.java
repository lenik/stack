package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.people.entity.Org;

public class OrgDto
        extends AbstractPartyDto<Org> {

    private static final long serialVersionUID = 1L;

    OrgTypeDto type;
    int size;

    public OrgDto() {
        super();
    }

    public OrgDto(Org source) {
        super(source);
    }

    public OrgDto(int selection) {
        super(selection);
    }

    public OrgDto(int selection, Org source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(Org source) {
        type = new OrgTypeDto(source.getType());
        size = source.getSize();
    }

    @Override
    protected void _unmarshalTo(Org target) {
        merge(target, "type", type);
        target.setSize(size);
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



}
