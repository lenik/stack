package com.bee32.plover.site;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.free.Strings;

import com.bee32.plover.arch.util.OrderComparator;

public class PageMap {

    Set<IPageGenerator> index;
    Map<String, IPageGenerator> map;

    public PageMap() {
        index = new TreeSet<IPageGenerator>(OrderComparator.INSTANCE);
        map = new HashMap<String, IPageGenerator>();
    }

    public void add(String name, Class<?> pageClass) {
        InstantiatePageGenerator page = new InstantiatePageGenerator(pageClass);
        map.put(name, page);
        index.add(page);
    }

    public void add(Class<?> pageClass) {
        String typeName = pageClass.getSimpleName();
        String name = Strings.lcfirst(typeName);
        add(name, pageClass);
    }

    public void add(String name, int order, Object content) {
        IPageGenerator page = new SimplePageGenerator(order, content);
        map.put(name, page);
        index.add(page);
    }

    public IPageGenerator getPage(String name) {
        IPageGenerator page = map.get(name);
        return page;
    }

}
