package com.bee32.sem.people.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("ORG")
public class Org
        extends Party {

    private static final long serialVersionUID = 1L;

    OrgType type;
    int size;


    @ManyToOne
    public OrgType getType() {
        return type;
    }

    public void setType(OrgType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    /**
     * 企业规模：员工人数
     */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
