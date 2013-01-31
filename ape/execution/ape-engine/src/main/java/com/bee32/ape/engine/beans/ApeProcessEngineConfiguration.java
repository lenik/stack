package com.bee32.ape.engine.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.inject.Inject;

import org.activiti.engine.form.AbstractFormType;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bee32.ape.engine.base.IAppCtxAccess;
import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "data source")
public class ApeProcessEngineConfiguration
        extends SpringProcessEngineConfiguration
        implements IAppCtxAccess, InitializingBean {

    static final Logger logger = LoggerFactory.getLogger(ApeProcessEngineConfiguration.class);

    @Inject
    ApplicationContext appctx;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        List<AbstractFormType> mutableList = new ArrayList<>();
        setCustomFormTypes(mutableList);

        // TODO Job executor in another thread?
        // setJobExecutorActivate(true);

        for (IProcessEngineConfigurer configurer : ServiceLoader.load(IProcessEngineConfigurer.class)) {
            if (configurer instanceof ApplicationContextAware) {
                ((ApplicationContextAware) configurer).setApplicationContext(appctx);
            }
            configurer.configureSpring(this);
        }
    }

}
