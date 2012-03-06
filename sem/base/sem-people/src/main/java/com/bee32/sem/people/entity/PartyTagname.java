package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.bee32.plover.ox1.dict.NameDict;

@Entity
public class PartyTagname
        extends NameDict {

    private static final long serialVersionUID = 1L;

    boolean internal;
    Set<Party> instances;

    public PartyTagname() {
        super();
    }

    public PartyTagname(String name, String label) {
        super(name, label);
    }

    public PartyTagname(String name, String label, boolean internal) {
        super(name, label);
        this.internal = internal;
    }

X-Population

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    /**
     * @see Party#getTags()
     */
    @ManyToMany(mappedBy = "tags")
    public Set<Party> getInstances() {
        if (instances == null) {
            synchronized (this) {
                if (instances == null) {
                    instances = new HashSet<Party>();
                }
            }
        }
        return instances;
    }

    public void setInstances(Set<Party> instances) {
        if (instances == null)
            throw new NullPointerException("instances");
        this.instances = instances;
    }

}
