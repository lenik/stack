package com.bee32.sem.people.dao;

import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonXP;

public class PersonDao<E extends Person>
        extends EntityExtDao<E, Integer, PersonXP> {

}
