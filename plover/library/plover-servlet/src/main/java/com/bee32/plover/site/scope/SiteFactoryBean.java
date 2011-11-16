package com.bee32.plover.site.scope;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

@Component("site")
@PerSite
public class SiteFactoryBean
        implements FactoryBean<SiteInstance> {

    static final SiteManager manager = SiteManager.getInstance();

    @Override
    public SiteInstance getObject()
            throws Exception {
        return ThreadHttpContext.getSiteInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return SiteInstance.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
