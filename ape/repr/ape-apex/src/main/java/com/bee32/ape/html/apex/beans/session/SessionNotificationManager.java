package com.bee32.ape.html.apex.beans.session;

import javax.inject.Inject;

import org.activiti.explorer.I18nManager;
import org.activiti.explorer.NotificationManager;
import org.activiti.explorer.ui.MainWindow;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;

@Component
@PerSession
public class SessionNotificationManager
        extends NotificationManager {

    private static final long serialVersionUID = 1L;

    @Inject
    @Override
    public void setMainWindow(MainWindow mainWindow) {
        super.setMainWindow(mainWindow);
    }

    @Inject
    @Override
    public void setI18nManager(I18nManager i18nManager) {
        super.setI18nManager(i18nManager);
    }

}
