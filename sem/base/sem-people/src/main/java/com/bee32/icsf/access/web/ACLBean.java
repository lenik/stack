package com.bee32.icsf.access.web;

import java.util.List;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.icsf.access.acl.ACLEntryDto;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ACLBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ACLBean() {
        super(ACL.class, ACLDto.class, ACLDto.INHERITED_ACL);
    }

    // editForm

    ACLDto selectedInheritedACL;
    ACLEntryDto selectedEntry;

    public ACLDto getSelectedInheritedACL() {
        return selectedInheritedACL;
    }

    public void setSelectedInheritedACL(ACLDto selectedInheritedACL) {
        this.selectedInheritedACL = selectedInheritedACL;
    }

    public void confirmInheritedACL() {
        ACLDto acl = getActiveObject();
        acl.setInheritedACL(selectedInheritedACL);
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
    PrincipalDto selectedEntryPrincipal;

    public ACLEntryDto getEntry() {
        return entry;
    }

    public PrincipalDto getSelectedEntryPrincipal() {
        return selectedEntryPrincipal;
    }

    public void setSelectedEntryPrincipal(PrincipalDto selectedEntryPrincipal) {
        this.selectedEntryPrincipal = selectedEntryPrincipal;
    }

    public void confirmEntryPrincipal() {
        entry.setPrincipal(selectedEntryPrincipal);
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
