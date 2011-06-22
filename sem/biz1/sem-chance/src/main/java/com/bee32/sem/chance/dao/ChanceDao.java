package com.bee32.sem.chance.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.chance.entity.Chance;

public class ChanceDao
        extends EntityDao<Chance, Long> {

    public List<Chance> keywordList(IUserPrincipal user, String keyword) {
        Criteria criteria = getSession().createCriteria(Chance.class);
        criteria.add(Restrictions.like("subject", "%" + keyword + "%"));
        criteria.add(Restrictions.eq("owner.id", user.getId()));
        return criteria.list();
    }

    public List<Chance> limitedList(IUserPrincipal user, int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(Chance.class);
        criteria.add(Restrictions.eq("owner.id", user.getId()));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    public List<Chance> limitedSearchList(IUserPrincipal user, String keyword, int start, int pageSize) {
        Criteria criteria = getSession().createCriteria(Chance.class);
        criteria.add(Restrictions.like("subject", "%"+ keyword+"%"));
        criteria.add(Restrictions.eq("owner.id", user.getId()));
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }
}
