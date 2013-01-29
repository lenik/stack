package com.bee32.ape.html.apex.beans.session;

import javax.inject.Inject;

import org.activiti.explorer.ViewManagerFactoryBean;
import org.activiti.explorer.ui.MainWindow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.bee32.ape.html.apex.beans.ApexConfig;
import com.bee32.plover.inject.scope.PerSession;

@Component
@PerSession
public class SessionViewManagerFactoryBean
        extends ViewManagerFactoryBean
        implements InitializingBean {

    @Inject
    ApexConfig config;

    @Inject
    @Override
    public void setMainWindow(MainWindow mainWindow) {
        super.setMainWindow(mainWindow);
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        setEnvironment(config.getEnvironment());
    }

}
