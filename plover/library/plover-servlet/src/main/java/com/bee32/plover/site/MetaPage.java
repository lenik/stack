package com.bee32.plover.site;

import java.lang.reflect.Constructor;

import javax.free.Order;

import com.bee32.plover.arch.util.IOrdered;
import com.bee32.plover.arch.util.TextMap;

public class MetaPage
        implements IOrdered {

    final int order;
    final Class<?> pageClass;
    final Constructor<?> pageCtor;

    public MetaPage(Class<?> pageClass) {
        if (pageClass == null)
            throw new NullPointerException("pageClass");

        Order _order = pageClass.getAnnotation(Order.class);
        order = _order == null ? 0 : _order.value();

        this.pageClass = pageClass;

        try {
            pageCtor = pageClass.getConstructor(TextMap.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Class<?> getPageClass() {
        return pageClass;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public String render(TextMap args) {
        Object page;
        try {
            page = pageCtor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return page.toString();
    }

}
