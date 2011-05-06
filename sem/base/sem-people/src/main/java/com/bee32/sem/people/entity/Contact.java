package com.bee32.sem.people.entity;

import java.util.List;

import javax.free.Person;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 联系人。为“客户联系人”和“供应商联系人”的基类， 因为有共同的地方，所以提取本类
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Contact
        extends EntityExt<Long, ContactXP> {

    private static final long serialVersionUID = 1L;

    Person person;
    ContactCategory category;

    String email;
    String website;
    String qq;

    String telephone;
    String cellphone;
    String fax;

    String postcode;
    String address;

    @Override
    protected List<ContactXP> getXPool() {
        return pool();
    }

    @Override
    protected void setXPool(List<ContactXP> xPool) {
        pool(xPool);
    }

}
