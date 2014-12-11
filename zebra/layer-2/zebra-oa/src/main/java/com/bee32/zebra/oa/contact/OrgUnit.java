package com.bee32.zebra.oa.contact;

import java.util.ArrayList;
import java.util.List;

import com.tinylily.model.base.CoNode;

public class OrgUnit
        extends CoNode<OrgUnit> {

    private static final long serialVersionUID = 1L;

    private int id;
    private Organization org;
    private Contact contact;
    private List<PersonRole> staff = new ArrayList<PersonRole>();

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public void setOrgId(int orgId) {
        (this.org = new Organization()).setId(orgId);
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setContactId(int contactId) {
        (this.contact = new Contact()).setId(contactId);
    }

    public List<PersonRole> getStaff() {
        return staff;
    }

}