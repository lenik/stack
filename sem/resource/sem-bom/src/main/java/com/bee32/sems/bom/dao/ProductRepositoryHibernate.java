package com.bee32.sems.bom.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.bee32.plover.orm.entity.EntityImplDao;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.bom.entity.ProductRepository;
import com.bee32.sems.material.entity.MaterialImpl;

public class ProductRepositoryHibernate
        extends EntityImplDao<Product, Long>
        implements ProductRepository {

    @Override
    public List<Product> listPart(Integer start, Integer count) {
        return getSession().createCriteria(Product.class).setFirstResult(start).setMaxResults(count).list();
    }

    @Override
    public Product findByMaterial(MaterialImpl material) {
        return (Product) getSession().createCriteria(Product.class).add(Restrictions.eq("material", material)).uniqueResult();
    }


}
