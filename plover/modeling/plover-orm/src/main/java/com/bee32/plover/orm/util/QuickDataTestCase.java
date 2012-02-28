package com.bee32.plover.orm.util;

import org.junit.Assert;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.SuperSamplePackage.Normals;

public class QuickDataTestCase
        extends Assert {

    protected static class ctx
            extends DefaultDataAssembledContext {

        public static final DataPartialContext data = new DataPartialContext() {

            @Override
            public CommonDataManager getDataManager() {
                return MemdbDataManager.getInstance();
            }
        };

    }

    public QuickDataTestCase() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);

        SamplesLoader samplesLoader = new SamplesLoader();
        SamplePackage.allocation = SamplePackageAllocation.STATIC;
        samplesLoader.loadSamples(Normals.class);
    }

}
