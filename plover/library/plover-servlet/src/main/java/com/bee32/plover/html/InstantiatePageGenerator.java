package com.bee32.plover.html;

import java.lang.reflect.Constructor;
import java.util.Map;

import javax.free.Order;

import com.bee32.plover.site.IPageGenerator;

public class InstantiatePageGenerator
        implements IPageGenerator {

    final int order;

    final Class<?> pageClass;
    final Constructor<?> pageCtor;

    public InstantiatePageGenerator(Class<?> pageClass) {
        if (pageClass == null)
            throw new NullPointerException("pageClass");

        Order _order = pageClass.getAnnotation(Order.class);
        order = _order == null ? 0 : _order.value();

        this.pageClass = pageClass;

        try {
            pageCtor = pageClass.getConstructor(Map.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public Class<?> getPageClass() {
        return pageClass;
    }

    public Constructor<?> getPageCtor() {
        return pageCtor;
    }

    @Override
    public String generate(Map<String, ?> args) {
        Object page;
        try {
            page = pageCtor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return page.toString();
    }

}
