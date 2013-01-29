package com.bee32.ape.html.apex.beans.session;

import javax.inject.Inject;

import org.activiti.explorer.navigation.NavigationFragmentChangeListener;
import org.activiti.explorer.navigation.NavigatorManager;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;

@Component
@PerSession
public class SessionNavigationFragmentChangeListener
        extends NavigationFragmentChangeListener {

    private static final long serialVersionUID = 1L;

    @Inject
    @Override
    public void setNavigatorManager(NavigatorManager navigatorManager) {
        super.setNavigatorManager(navigatorManager);
    }

}
