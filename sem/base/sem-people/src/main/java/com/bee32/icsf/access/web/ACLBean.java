package com.bee32.icsf.access.web;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.icsf.access.acl.ACLEntryDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ACLBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ACLBean() {
        super(ACL.class, ACLDto.class, 0);
    }

    // editForm

    ACLDto selectedACL;
    ACLEntryDto selectedEntry;

    public ACLDto getSelectedACL() {
        return selectedACL;
    }

    public void setSelectedACL(ACLDto selectedACL) {
        this.selectedACL = selectedACL;
    }

    public void setInheritedACL() {
        ACLDto acl = getActiveObject();
        acl.setInheritedACL(selectedACL);
    }

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
        acl.getEntries().add(entry);
        entry = null;
    }

}
