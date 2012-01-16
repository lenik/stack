package com.bee32.icsf.access.web;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.icsf.access.acl.ACLEntryDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ACLBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ACLEntryDto selectedEntry;
    ACLEntryDto entry;

    public ACLBean() {
        super(ACL.class, ACLDto.class, 0);
    }

    public void addEntry() {
        ACLDto acl = getActiveObject();
        acl.getEntries().add(entry);
    }

    public void removeEntry() {
        ACLDto acl = getActiveObject();
        acl.getEntries().remove(selectedEntry);
    }

    public ACLEntryDto getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(ACLEntryDto selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public ACLEntryDto getEntry() {
        return entry;
    }

    public void setEntry(ACLEntryDto entry) {
        this.entry = entry;
    }

}
