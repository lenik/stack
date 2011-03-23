package com.bee32.sem.frame.builtins;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.sem.frame.menu.MenuBar;
import com.bee32.sem.frame.menu.MenuContribution;

@Component
@Lazy
public class MainMenu
        extends MenuBar
        implements InitializingBean {

    @Inject
    private ApplicationContext context;

    MainMenu() {
        super("main");
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        for (MenuContribution menuContrib : context.getBeansOfType(MenuContribution.class).values()) {

        }
    }

}
