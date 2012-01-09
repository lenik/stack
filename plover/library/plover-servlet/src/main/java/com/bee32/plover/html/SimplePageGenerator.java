package com.bee32.plover.html;

import java.util.Map;

import com.bee32.plover.site.IPageGenerator;

public class SimplePageGenerator
        implements IPageGenerator {

    int order;
    Object page;

    public SimplePageGenerator(Object page) {
        this(0, page);
    }

    public SimplePageGenerator(int order, Object page) {
        if (page == null)
            throw new NullPointerException("page");
        this.order = order;
        this.page = page;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String generate(Map<String, ?> args) {
        return page.toString();
    }

}
