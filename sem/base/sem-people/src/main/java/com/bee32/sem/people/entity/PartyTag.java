package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class PartyTag
        extends NameDict {

    private static final long serialVersionUID = 1L;

    Set<Party> instances;

    public PartyTag() {
        super();
    }

    public PartyTag(String name, String label) {
        super(name, label);
    }

    public PartyTag(String name, String label, String description) {
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

    /**
     * @.name employee
     * @.label Employee
     * @.label.zh_CN 雇员
     */
    public static final PartyTag EMPLOYEE = new PartyTag("employee", "雇员");

    /**
     * @.name customer
     * @.label Customer
     * @.label.zh_CN 客户
     */
    public static final PartyTag CUSTOMER = new PartyTag("customer", "客户");

    /**
     * @.name supplier
     * @.label Supplier
     * @.label.zh_CN 供应商
     */
    public static final PartyTag SUPPLIER = new PartyTag("supplier", "供应商");

}
