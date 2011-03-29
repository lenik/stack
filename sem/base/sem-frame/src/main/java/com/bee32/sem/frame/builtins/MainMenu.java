package com.bee32.sem.frame.builtins;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.sem.frame.menu.MenuBar;
import com.bee32.sem.frame.menu.MenuContribution;

@Component
@Lazy
public class MainMenu
        extends MenuBar
        implements ApplicationContextAware {

    MainMenu() {
        super("main");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        for (MenuContribution contribution : applicationContext.getBeansOfType(MenuContribution.class).values()) {
            merge(contribution);
        }

    }

}
