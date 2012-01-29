package com.bee32.icsf.access.web;

import java.util.List;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.icsf.access.acl.ACLEntryDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ACLBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ACLBean() {
        super(ACL.class, ACLDto.class, ACLDto.INHERITED_ACL);
    }

    // editForm

    ACLEntryDto selectedEntry;

    public void clearInheritedACL() {
        ACLDto acl = getActiveObject();
        acl.setInheritedACL(new ACLDto());
    }

    public ACLEntryDto getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(ACLEntryDto selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public void removeEntry() {
        ACLDto acl = getActiveObject();
        acl.getEntries().remove(selectedEntry);
    }

    public void createEntry() {
        entry = new ACLEntryDto().create();
    }

    public void editEntry() {
        if (selectedEntry == null)
            return;
        entry = reload(selectedEntry);
    }

    // editEntryForm

    ACLEntryDto entry;

    public ACLEntryDto getEntry() {
        return entry;
    }

    public void addEntry() {
        if (entry == null)
            throw new NullPointerException("entry");
        ACLDto acl = getActiveObject();
        entry.setACL(acl);

        List<ACLEntryDto> entries = acl.getEntries();
        if (!entries.contains(entry))
            entries.add(entry);

        entry = null;
    }

}
