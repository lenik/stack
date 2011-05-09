package com.bee32.sem.people.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class PersonTag
        extends NameDict {

    private static final long serialVersionUID = 1L;

    List<PersonTag> instances;

    /**
     * @see Person#getTags()
     */
    @ManyToMany(mappedBy = "tag")
    public List<PersonTag> getInstances() {
        if (instances == null) {
            synchronized (this) {
                if (instances == null) {
                    instances = new ArrayList<PersonTag>();
                }
            }
        }
        return instances;
    }

    public void setInstances(List<PersonTag> instances) {
        this.instances = instances;
    }

    /**
     * @.name employee
     * @.label Employee
     * @.label.zh_CN 雇员
     */
    public static final PersonTag EMPLOYEE = new PersonTag();

    /**
     * @.name customer
     * @.label Customer
     * @.label.zh_CN 客户
     */
    public static final PersonTag CUSTOMER = new PersonTag();

    /**
     * @.name supplier
     * @.label Supplier
     * @.label.zh_CN 供应商
     */
    public static final PersonTag SUPPLIER = new PersonTag();

}
