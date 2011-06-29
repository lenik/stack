package com.bee32.icsf.access.dacl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class DACL
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    // Not yet used.
    DACL parent;

    Principal owner;
    List<DACE> entries;

    /**
     * Not used, yet.
     */
    @ManyToOne
    public DACL getParent() {
        return parent;
    }

    public void setParent(DACL parent) {
        this.parent = parent;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Principal getOwner() {
        return owner;
    }

    public void setOwner(Principal owner) {
        this.owner = owner;
    }

    @OneToMany
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<DACE> getEntries() {
        return entries;
    }

    public void setEntries(List<DACE> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

    public boolean addEntry(DACE entry) {
        if (entries.contains(entry))
            return false;
        entries.add(entry);
        return true;
    }

    public boolean removeEntry(DACE entry) {
        return entries.remove(entry);
    }

}
