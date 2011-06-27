package com.bee32.sem.people.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;

public class PartyDao<E extends Party>
        extends EntityExtDao<E, Integer, PartyXP> {

    public List<E> limitedKeywordList(Class<E> entityClass, String keyword, int displayStart, int displayLength){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.add(Restrictions.like("name", "%" + keyword + "%"));
        criteria.setFirstResult(displayStart);
        criteria.setMaxResults(displayLength);
        return criteria.list();
    }



    public List<E> limitedKeywordList(Class<E> entityClass, IUserPrincipal user, String keyword, int displayStart, int displayLength){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.add(Restrictions.like("name", "%" + keyword + "%"));
        criteria.add(Restrictions.eq("owner.id", user.getId()));
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(displayStart);
        criteria.setMaxResults(displayLength);
        return criteria.list();
    }

    public long limitedKeywordListCount(Class<E> entityClass, IUserPrincipal user, String keyword) {
		int count = (Integer) getSession()
				.createCriteria(entityClass)
				.add(Restrictions.like("name", "%" + keyword + "%"))
				.add(Restrictions.eq("owner.id", user.getId()))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		long countL = count;
		return countL;
    }

    public List<E> limitedKeywordList(Class<E> entityClass, IUserPrincipal user, String keyword){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.add(Restrictions.like("name", "%" + keyword + "%"));
        criteria.add(Restrictions.eq("owner.id", user.getId()));
        return criteria.list();
    }

}
