package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.context.TestDataConfig;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.sample.SamplesLoader;
import com.bee32.plover.orm.sample.SuperSamplePackage.Normals;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.test.WiredTestCase;

@Import({ TestDataConfig.class })
public abstract class WiredDaoTestCase
        extends WiredTestCase {

    @Inject
    protected CommonDataManager dataManager = MemdbDataManager.getInstance();

    public WiredDaoTestCase() {
    }

    @Override
    protected void prewire() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        super.applicationInitialized(applicationContext);

        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadSamples(Normals.class);
    }

    protected <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        return dataManager.asFor(entityType);
    }

}
