package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class PartyTagname
        extends NameDict {

    private static final long serialVersionUID = 1L;

    Set<Party> instances;

    public PartyTagname() {
        super();
    }

    public PartyTagname(String name, String label) {
        super(name, label);
    }

    public PartyTagname(String name, String label, String description) {
        super(name, label, description);
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

    public static final PartyTagname EMPLOYEE = new PartyTagname("EMP", "雇员");
    public static final PartyTagname CUSTOMER = new PartyTagname("CUS", "客户");
    public static final PartyTagname SUPPLIER = new PartyTagname("SUP", "供应商");

}
