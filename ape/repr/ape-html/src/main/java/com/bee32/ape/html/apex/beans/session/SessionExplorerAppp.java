package com.bee32.ape.html.apex.beans.session;

import javax.inject.Inject;

import org.activiti.explorer.ComponentFactories;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.I18nManager;
import org.activiti.explorer.NotificationManager;
import org.activiti.explorer.ViewManager;
import org.activiti.explorer.cache.UserCache;
import org.activiti.explorer.ui.MainWindow;
import org.activiti.explorer.ui.content.AttachmentRendererManager;
import org.activiti.explorer.ui.form.FormPropertyRendererManager;
import org.activiti.explorer.ui.login.LoginHandler;
import org.activiti.explorer.ui.variable.VariableRendererManager;
import org.activiti.workflow.simple.converter.WorkflowDefinitionConversionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.bee32.ape.html.apex.beans.ApexConfig;
import com.bee32.plover.inject.scope.PerSession;

@Component
@PerSession
public class SessionExplorerAppp
        extends ExplorerApp
        implements InitializingBean {

    private static final long serialVersionUID = 1L;

    @Inject
    ApexConfig config;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        setEnvironment(config.getEnvironment());
    }

    @Inject
    @Override
    public void setUserCache(UserCache userCache) {
        super.setUserCache(userCache);
    }

    @Inject
    @Override
    public void setI18nManager(I18nManager i18nManager) {
        super.setI18nManager(i18nManager);
    }

    @Inject
    @Override
    public void setViewManager(ViewManager viewManager) {
        super.setViewManager(viewManager);
    }

    @Inject
    @Override
    public void setNotificationManager(NotificationManager notificationManager) {
        super.setNotificationManager(notificationManager);
    }

    @Inject
    @Override
    public void setAttachmentRendererManager(AttachmentRendererManager attachmentRendererManager) {
        super.setAttachmentRendererManager(attachmentRendererManager);
    }

    @Inject
    @Override
    public void setFormPropertyRendererManager(FormPropertyRendererManager formPropertyRendererManager) {
        super.setFormPropertyRendererManager(formPropertyRendererManager);
    }

    @Inject
    @Override
    public void setVariableRendererManager(VariableRendererManager variableRendererManager) {
        super.setVariableRendererManager(variableRendererManager);
    }

    @Inject
    @Override
    public void setApplicationMainWindow(MainWindow mainWindow) {
        super.setApplicationMainWindow(mainWindow);
    }

    @Inject
    @Override
    public void setComponentFactories(ComponentFactories componentFactories) {
        super.setComponentFactories(componentFactories);
    }

    @Inject
    @Override
    public void setLoginHandler(LoginHandler loginHandler) {
        super.setLoginHandler(loginHandler);
    }

    @Inject
    @Override
    public void setWorkflowDefinitionConversionFactory(
            WorkflowDefinitionConversionFactory workflowDefinitionConversionFactory) {
        super.setWorkflowDefinitionConversionFactory(workflowDefinitionConversionFactory);
    }

}
