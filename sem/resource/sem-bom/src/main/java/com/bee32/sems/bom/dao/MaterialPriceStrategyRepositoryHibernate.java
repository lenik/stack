package com.bee32.sems.bom.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.bee32.plover.orm.dao.GenericDao;
import com.bee32.sems.bom.entity.MaterialPriceStrategy;
import com.bee32.sems.bom.entity.MaterialPriceStrategyRepository;

@Repository("materialPriceStrategyRepository")
@Lazy
public class MaterialPriceStrategyRepositoryHibernate
        extends GenericDao
        implements MaterialPriceStrategyRepository {


    @Override
    public MaterialPriceStrategy get() {
        return (MaterialPriceStrategy) getSession().createCriteria(MaterialPriceStrategy.class).uniqueResult();
    }

    @Override
    public void save(MaterialPriceStrategy materialPriceStrategy) {
        MaterialPriceStrategy s = get();
        if(s != null) {
            s.setMemo(materialPriceStrategy.getMemo());
            s.setStrategy(materialPriceStrategy.getStrategy());
        } else {
            s = materialPriceStrategy;
        }

        getSession().saveOrUpdate(s);
    }
}
