package com.bee32.sem.people.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Org
        extends Party {

    private static final long serialVersionUID = 1L;

    OrgType type;
    int size;

    List<OrgContact> contacts = new ArrayList<OrgContact>();

    String interests;

    @ManyToOne(optional = false)
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

    /**
     * 主营业务
     */
    @Column(length = 200)
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    @OneToMany
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<OrgContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<OrgContact> contacts) {
        if (contacts == null)
            throw new NullPointerException("contacts");
        this.contacts = contacts;
    }

}
