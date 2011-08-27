package com.bee32.icsf.access.dacl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.tree.TreeEntityAuto;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "dacl_seq", allocationSize = 1)
public class DACL
        extends TreeEntityAuto<Integer, DACL> {

    private static final long serialVersionUID = 1L;

    List<DACE> entries;

    @OneToMany(mappedBy = "dacl")
    @Cascade(CascadeType.ALL)
    public List<DACE> getEntries() {
        return entries;
    }

    public void setEntries(List<DACE> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

    public boolean addEntry(DACE entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        if (entries.contains(entry))
            return false;
        entries.add(entry);
        return true;
    }

    public boolean removeEntry(DACE entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        return entries.remove(entry);
    }

}
