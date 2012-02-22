package com.bee32.icsf.access.alt;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public class R_ACLDto
        extends EntityDto<R_ACL, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int ENTRIES = 1;

    PrincipalDto principal;
    List<R_ACEDto> entries;

    @Override
    protected void _marshal(R_ACL source) {
        principal = mref(PrincipalDto.class, source.getPrincipal());

        if (selection.contains(ENTRIES))
            entries = marshalList(R_ACEDto.class, source.getEntries());
        else
            entries = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(R_ACL target) {
        merge(target, "principal", principal);
        if (selection.contains(ENTRIES))
            mergeList(target, "entries", entries);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public PrincipalDto getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDto principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    public List<R_ACEDto> getEntries() {
        return entries;
    }

    public void setEntries(List<R_ACEDto> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

}
