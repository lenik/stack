package com.bee32.sem.frame.menu;

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

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        for (MenuContribution contribution : applicationContext.getBeansOfType(MenuContribution.class).values()) {
            logger.debug("Merge menu contribution: " + contribution);

            // Force load of NLS.
            contribution.prepareMap();
            // merge(contribution);
        }
    }

}
