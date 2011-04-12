package com.bee32.plover.restful;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class RESTfulViewManager
        implements ApplicationContextAware {

    private final Set<IRESTfulView> views;

    public RESTfulViewManager() {
        views = new TreeSet<IRESTfulView>(RESTfulViewComparator.INSTANCE);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        for (IRESTfulView view : applicationContext.getBeansOfType(IRESTfulView.class).values()) {
            views.add(view);
        }
    }

    public Set<IRESTfulView> getViews() {
        return Collections.unmodifiableSet(views);
    }

}
