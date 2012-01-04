package com.bee32.plover.html;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

import javax.free.Order;

import com.bee32.plover.site.IPageGenerator;

public class InstantiatePageGenerator
        implements IPageGenerator {

    final int order;

    final Class<? extends AbstractHtmlTemplate> pageClass;
    final Constructor<? extends AbstractHtmlTemplate> pageCtor;

    public InstantiatePageGenerator(Class<? extends AbstractHtmlTemplate> pageClass) {
        if (pageClass == null)
            throw new NullPointerException("pageClass");

        Order _order = pageClass.getAnnotation(Order.class);
        order = _order == null ? 0 : _order.value();

        this.pageClass = pageClass;

        try {
            pageCtor = pageClass.getConstructor(Map.class);
            pageCtor.setAccessible(true);
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
        AbstractHtmlTemplate template;
        try {
            template = pageCtor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        try {
            template.instantiateOnce();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return template.toString();
    }

}
