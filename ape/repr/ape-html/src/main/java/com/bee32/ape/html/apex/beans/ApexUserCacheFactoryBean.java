package com.bee32.ape.html.apex.beans;

import javax.inject.Inject;

import org.activiti.explorer.cache.UserCacheFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "appctx in use.")
public class ApexUserCacheFactoryBean
        extends UserCacheFactoryBean
        implements InitializingBean {

    @Inject
    ApexConfig config;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        setEnvironment(config.environment);
    }

}
