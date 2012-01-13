package com.bee32.icsf.access.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.tree.TreeEntityDto;

public class ACLDto
        extends TreeEntityDto<ACL, Integer, ACLDto> {

    private static final long serialVersionUID = 1L;

    public static final int ENTRIES = 1;

    List<ACEDto> entries;

    public ACLDto() {
        super();
    }

    public ACLDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(ACL source) {
        if (selection.contains(ENTRIES)) {
            for (Entry<Principal, Permission> _entry : source.getEntries()) {
                PrincipalDto principal = mref(PrincipalDto.class, _entry.getKey());
                Permission permission = _entry.getValue().clone();
                ACEDto entry = new ACEDto(principal, permission);
                entries.add(entry);
            }
        } else {
            entries = new ArrayList<ACEDto>();
        }
    }

    @Override
    protected void _unmarshalTo(ACL target) {
        if (selection.contains(ENTRIES)) {
            target.getEntryMap().clear();
            for (ACEDto entry : entries) {
                Principal _principal = entry.getPrincipal().unmarshal(getSession());
                Permission _permission = entry.getPermission().clone();
                target.add(_principal, _permission);
            }
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<ACEDto> getEntries() {
        return entries;
    }

}
