package com.bee32.icsf.principal.dao;

import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityDao;

public class AbstractPrincipalDao<E extends Principal>
        extends EntityDao<E, Integer> {

    public E getByName(String name) {
        E user = (E) getSession().createCriteria(getEntityType())//
                .add(Restrictions.eq("name", name)).uniqueResult();
        return user;
    }

}
