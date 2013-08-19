package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.xp.XPool30;

/**
 * 联系信息扩展属性池
 *
 * 用于扩展联系信息。
 *
 * <p lang="en">
 * Contact Information Attribute Pool
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "contactxp_seq", allocationSize = 1)
public class ContactXP
        extends XPool30<Contact> {

    private static final long serialVersionUID = 1L;

}
