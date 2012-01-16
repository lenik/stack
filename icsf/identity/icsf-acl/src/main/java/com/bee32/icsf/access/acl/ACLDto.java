package com.bee32.icsf.access.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    List<ACLEntryDto> entries;

    public ACLDto() {
        super();
    }

    public ACLDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(ACL source) {
        if (selection.contains(ENTRIES)) {
            for (Entry<Principal, Permission> _entry : source.getDeclaredEntries().entrySet()) {
                PrincipalDto principal = mref(PrincipalDto.class, _entry.getKey());
                Permission permission = _entry.getValue().clone();
                ACLEntryDto entry = new ACLEntryDto();
                entry.setACL(this);
                entry.setPrincipal(principal);
                entry.setPermission(permission);
                entries.add(entry);
            }
        } else {
            entries = new ArrayList<ACLEntryDto>();
        }
    }

    @Override
    protected void _unmarshalTo(ACL target) {
        if (selection.contains(ENTRIES)) {
            Map<Principal, ACLEntry> map = target.getEntryMap();
            map.clear();
            for (ACLEntryDto entry : entries) {
                Principal _principal = entry.getPrincipal().unmarshal(getSession());
                ACLEntry _entry = entry.unmarshal();
                map.put(_principal, _entry);
            }
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
        setParent(inheritedDto);
    }

    public List<ACLEntryDto> getEntries() {
        return entries;
    }

}
