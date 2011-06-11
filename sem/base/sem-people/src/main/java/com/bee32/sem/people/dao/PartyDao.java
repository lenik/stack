package com.bee32.sem.people.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;

public class PartyDao<E extends Party>
        extends EntityExtDao<E, Integer, PartyXP> {

    public List<E> limitedList(Class<E> entityClass, int displayStart, int displayLength){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setFetchSize(displayStart);
        criteria.setMaxResults(displayLength);
        return criteria.list();
    }

    public List<E> limitedKeywordList(Class<E> entityClass, String keyword, int displayStart, int displayLength){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.add(Restrictions.like("name", keyword));
        criteria.setFirstResult(displayStart);
        criteria.setMaxResults(displayLength);
        return criteria.list();
    }
}
