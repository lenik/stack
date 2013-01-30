package com.bee32.ape.engine.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.form.AbstractFormType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bee32.ape.engine.base.IAppCtxAware;
import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "data source")
public class ApeProcessEngineConfiguration
        extends StandaloneProcessEngineConfiguration
        implements IAppCtxAware {

    static final Logger logger = LoggerFactory.getLogger(ApeProcessEngineConfiguration.class);

    public ApeProcessEngineConfiguration() {
        List<AbstractFormType> mutableList = new ArrayList<>();
        setCustomFormTypes(mutableList);

        // TODO Job executor in another thread?
        // setJobExecutorActivate(true);

        for (IProcessEngineConfigurer configurer : ServiceLoader.load(IProcessEngineConfigurer.class)) {
            configurer.processEngineConfigure(this);
        }
    }

}
