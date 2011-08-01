package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.ext.xp.XPool30;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "contactxp_seq", allocationSize = 1)
public class ContactXP
        extends XPool30<Contact> {

    private static final long serialVersionUID = 1L;

}
