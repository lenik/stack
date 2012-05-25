package com.bee32.sem.frame.menu;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class MenuLoader
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(MenuLoader.class);

    boolean menuLoaded;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        loadMenus(applicationContext.getBeansOfType(MenuComposite.class).values());
    }

    synchronized void loadMenus(Collection<MenuComposite> comps) {
        for (MenuComposite comp : comps) {
            logger.debug("Merge menu composite: " + comp);

            // Force load of NLS.
            // comp.prepareMap();
            // merge(contribution);
        }
    }

}
