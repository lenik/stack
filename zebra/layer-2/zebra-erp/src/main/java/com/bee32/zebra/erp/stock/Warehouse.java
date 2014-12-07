package com.bee32.zebra.erp.stock;

import com.bee32.zebra.oa.contact.Contact;
import com.tinylily.model.base.CoEntity;

public class Warehouse
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    private int id;
    private Contact contact;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
