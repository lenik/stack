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

    public static final PartyTagname INTERNAL = new PartyTagname("-", "公司内部", true);
    public static final PartyTagname CUSTOMER = new PartyTagname("CUS", "客户");
    public static final PartyTagname SUPPLIER = new PartyTagname("SUP", "供应商");
    public static final PartyTagname ENEMY = new PartyTagname("ENY", "竞争对手");
    public static final PartyTagname OTHER = new PartyTagname("OTH", "其他");

}
