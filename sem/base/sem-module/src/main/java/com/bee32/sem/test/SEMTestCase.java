package com.bee32.sem.test;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.context.OSIVFilter;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.DispatchServlet;
import com.bee32.plover.restful.RESTfulConfig;
import com.bee32.plover.zk.test.ZkTestCase;

@Import(WiredDaoTestCase.class)
public class SEMTestCase
        extends ZkTestCase {

    protected String PREFIX = RESTfulConfig.preferredPrefix;

    public SEMTestCase() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        // OSIV filter should before dispatch filter.
        stl.addFilter(OSIVFilter.class, "/*", 0);

        // Also enable Restful service.
        stl.addServlet(DispatchServlet.class, PREFIX + "/*");
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadNormalSamples();
    }

}
