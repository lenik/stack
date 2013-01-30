package com.bee32.ape.html.apex.beans.session;

import org.activiti.explorer.I18nManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;

@Component
@PerSession
public class SessionI18nManager
        extends I18nManager
        implements InitializingBean {

    private static final long serialVersionUID = 1L;

    public SessionI18nManager() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        setMessageSource(messageSource);
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
    }

}
