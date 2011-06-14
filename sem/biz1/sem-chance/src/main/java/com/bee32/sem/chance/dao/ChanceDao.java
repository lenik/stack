package com.bee32.sem.chance.dao;

import java.util.List;

import javax.free.UnexpectedException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.chance.entity.Chance;

public class ChanceDao
        extends EntityDao<Chance, Long> {

    public List<Chance> limitedList(int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(Chance.class);
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    public List<Chance> limitedList(String keyword, int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(Chance.class);
        if (keyword != null && keyword.length() != 0)
            criteria.add(Restrictions.like("subject", keyword));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    //FIXME 返回空,奇怪
    public List<Chance> chanceSearch(String keyword){
        Criteria criteria = getSession().createCriteria(Chance.class);
        criteria.add(Restrictions.like("subject", keyword));
        return criteria.list();
    }

    public int getChanceCount(String keyword) {
        Criteria criteria = getSession().createCriteria(Chance.class);
        if (keyword != null && keyword.length() != 0)
            criteria.add(Restrictions.like("subject", keyword));
        criteria.setProjection(Projections.rowCount());
        Object result = criteria.uniqueResult();

        if (result == null)
            throw new UnexpectedException("getChanceCount() returns null");

        return ((Number) result).intValue();
    }

}
