package com.bee32.sem.frame.builtins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static Logger logger = LoggerFactory.getLogger(MainMenu.class);

    MainMenu() {
        super("main");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        for (MenuContribution contribution : applicationContext.getBeansOfType(MenuContribution.class).values()) {
            logger.debug("Merge menu contribution: " + contribution);
            merge(contribution);
        }

    }

}
