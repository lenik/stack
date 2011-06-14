package com.bee32.sem.chance.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceActionDao
        extends EntityDao<ChanceAction, Long> {

    public List<ChanceAction> limitedList(int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(ChanceAction.class);
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    public List<ChanceAction> limitedSearchList(Date date_start, Date date_end, int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(ChanceAction.class);
        criteria.add(Restrictions.between("beginTime", date_start, date_end));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }
}
