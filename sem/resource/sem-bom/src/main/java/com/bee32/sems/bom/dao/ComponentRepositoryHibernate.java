package com.bee32.sems.bom.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.bee32.plover.orm.entity.EntityImplDao;
import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.bom.entity.ComponentRepository;

public class ComponentRepositoryHibernate
        extends EntityImplDao<Component, Long>
        implements ComponentRepository {

    @SuppressWarnings("unchecked")
    @Override
    public List<Component> listByProduct(Long productId) {
        return getSession().createCriteria(Component.class).add(Restrictions.eq("product.id", productId)).list();
    }

}
