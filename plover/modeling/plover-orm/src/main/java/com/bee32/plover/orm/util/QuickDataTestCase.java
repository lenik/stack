package com.bee32.plover.orm.util;

import org.junit.Assert;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;

public class QuickDataTestCase
        extends Assert {

    protected static class ctx
            extends DefaultDataAssembledContext {

        public static final AbstractDataPartialContext data = new AbstractDataPartialContext() {

            @Override
            public CommonDataManager getDataManager() {
                return MemdbDataManager.getInstance();
            }
        };

    }

    public QuickDataTestCase() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);

        new StandardSamples();

        SamplesLoader samplesLoader = new SamplesLoader();
        // samplesLoader.loadSamples(DiamondPackage.NORMAL);
    }

}
