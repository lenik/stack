package com.bee32.sem.chance.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceActionDao
        extends EntityDao<ChanceAction, Long> {

    public List<ChanceAction> limitedList(IUserPrincipal user, int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(ChanceAction.class);
        criteria.add(Restrictions.eq("actor.id", user.getId()));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    public List<ChanceAction> limitedSearchList(IUserPrincipal user, Date date_start, Date date_end, int start,
            int pageSize) {
        Criteria criteria = getSession().createCriteria(ChanceAction.class);
        criteria.add(Restrictions.between("beginTime", date_start, date_end));
        criteria.add(Restrictions.eq("actor.id", user.getId()));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    public int limitedSearchListCount(IUserPrincipal user, Date date_start, Date date_end) {
        Criteria criteria = getSession().createCriteria(ChanceAction.class);
        criteria.add(Restrictions.between("beginTime", date_start, date_end));
        criteria.add(Restrictions.eq("actor.id", user.getId()));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.uniqueResult();
    }

    public List<ChanceAction> dateRangeList(IUserPrincipal user, Date begin, Date end){
        Criteria criteria = getSession().createCriteria(ChanceAction.class);
        criteria.add(Restrictions.eq("actor.id", user.getId()));
        criteria.add(Restrictions.between("beginTime", begin, end));
        return criteria.list();
    }
}
