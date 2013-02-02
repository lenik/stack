package com.bee32.ape.html.apex.beans.session;

import javax.inject.Inject;

import org.activiti.explorer.I18nManager;
import org.activiti.explorer.navigation.NavigationFragmentChangeListener;
import org.activiti.explorer.ui.MainWindow;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;

@Component
@PerSession
public class SessionMainWindow
        extends MainWindow {

    private static final long serialVersionUID = 1L;

    @Inject
    @Override
    public void setNavigationFragmentChangeListener(NavigationFragmentChangeListener navigationFragmentChangeListener) {
        super.setNavigationFragmentChangeListener(navigationFragmentChangeListener);
    }

    @Inject
    @Override
    public void setI18nManager(I18nManager i18nManager) {
        super.setI18nManager(i18nManager);
    }

}
