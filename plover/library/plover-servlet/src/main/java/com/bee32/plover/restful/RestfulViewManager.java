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
public class RestfulViewManager
        implements ApplicationContextAware {

    private final Set<IRestfulView> views;

    public RestfulViewManager() {
        views = new TreeSet<IRestfulView>(RestfulViewComparator.INSTANCE);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        for (IRestfulView view : applicationContext.getBeansOfType(IRestfulView.class).values()) {
            views.add(view);
        }
    }

    public Set<IRestfulView> getViews() {
        return Collections.unmodifiableSet(views);
    }

}
