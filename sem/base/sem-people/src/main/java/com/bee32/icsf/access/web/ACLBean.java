package com.bee32.icsf.access.web;

import java.util.List;

import com.bee32.icsf.access.acl.ACEDto;
import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.web.ChoosePrincipalDialogListener;

public class ACLBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ACEDto selectedEntry;
    ACEDto entry;

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

    public ACEDto getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(ACEDto selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public ACEDto getEntry() {
        return entry;
    }

    public void setEntry(ACEDto entry) {
        this.entry = entry;
    }

    public Object getSetEntryPrincipalAdapter() {
        return new ChoosePrincipalDialogListener() {
            @Override
            protected void selected(List<?> selection) {
                for (Object item : selection) {
                    entry.setPrincipal((PrincipalDto) item);
                    break;
                }
            }
        };
    }

}
