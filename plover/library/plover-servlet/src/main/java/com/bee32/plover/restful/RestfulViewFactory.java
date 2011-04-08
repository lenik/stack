package com.bee32.plover.restful;

import java.util.Collections;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

public class RestfulViewFactory {

    static Set<IRestfulView> views;
    static {
        views = new TreeSet<IRestfulView>(RestfulViewComparator.INSTANCE);

        for (IRestfulView view : ServiceLoader.load(IRestfulView.class))
            views.add(view);
    }

    public static Set<IRestfulView> getViews() {
        return Collections.unmodifiableSet(views);
    }

}
