package com.bee32.plover.orm.util;

import java.io.Serializable;

import org.junit.Assert;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;

public class QuickDataTestCase
        extends Assert
        implements IEntityMarshalContext {

    CommonDataManager dataManager = MemdbDataManager.getInstance();

    public QuickDataTestCase() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);

        new StandardSamples();

        SamplesLoader loader = new SamplesLoader();
        loader.loadNormalSamples();
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        return asFor(entityType).getOrFail(id);
    }

    @Override
    public <_E extends Entity<? extends _K>, _K extends Serializable> //
    IEntityAccessService<_E, _K> asFor(Class<? extends _E> entityType) {
        IEntityAccessService<_E, _K> service = dataManager.asFor(entityType);
        return service;
    }

}
