package com.bee32.plover.restful.test;

import java.io.IOException;

import javax.free.IllegalUsageException;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.sample.SamplesLoader;
import com.bee32.plover.orm.sample.SuperSamplePackage.Normals;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.RESTfulConfig;
import com.bee32.plover.servlet.test.WiredServletTestCase;

@Import(WiredDaoTestCase.class)
public abstract class RESTfulTestCase
        extends WiredServletTestCase {

    private boolean checkAdditionalServlets;

    public String PREFIX = RESTfulConfig.preferredPrefix;

    protected RESTfulTestCase() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadSamples(Normals.class);
    }

    @Override
    protected final void configureServlets() {
        configureAdditionalServlets();

        if (!checkAdditionalServlets)
            throw new IllegalUsageException("configureAdditionalServlets is overrided.");
    }

    protected void configureAdditionalServlets() {
        checkAdditionalServlets = true;
    }

    public void browseAndWait(Class<?> moduleClass)
            throws IOException {
        String modulePath = OidUtil.getOid(moduleClass).toPath();
        String location = PREFIX + "/" + modulePath + "/";
        browseAndWait(location);
    }

}
