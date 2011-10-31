package com.bee32.plover.site;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.free.Strings;

public class PageMap {

    List<IPageGenerator> index;
    Map<String, IPageGenerator> map;

    public PageMap() {
        index = new ArrayList<IPageGenerator>(); // (OrderComparator.INSTANCE);
        map = new LinkedHashMap<String, IPageGenerator>();
    }

    public void add(String name, Class<?> pageClass) {
        InstantiatePageGenerator page = new InstantiatePageGenerator(pageClass);
        index.add(page);
        map.put(name, page);
    }

    public void add(Class<?> pageClass) {
        String typeName = pageClass.getSimpleName();
        String name = Strings.lcfirst(typeName);
        add(name, pageClass);
    }

    public void add(String name, int order, Object content) {
        IPageGenerator page = new SimplePageGenerator(order, content);
        index.add(page);
        map.put(name, page);
    }

    public IPageGenerator getPage(String name) {
        IPageGenerator page = map.get(name);
        return page;
    }

}
