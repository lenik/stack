package com.bee32.sem.people.dao;

import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactXP;

public class ContactDao<E extends Contact> extends EntityExtDao<E, Integer, ContactXP> {


}
