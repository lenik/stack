package com.bee32.sem.people.dao;

import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;

public class PartyDao<E extends Party>
        extends EntityExtDao<E, Integer, PartyXP> {

}
