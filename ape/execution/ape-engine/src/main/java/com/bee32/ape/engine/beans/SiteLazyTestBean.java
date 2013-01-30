package com.bee32.ape.engine.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class SiteLazyTestBean
        implements InitializingBean, ApplicationContextAware {

    public SiteLazyTestBean() {
        System.err.println("+++ SITE LAZY +++");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
    }

}
