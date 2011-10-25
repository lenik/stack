package com.bee32.plover.site;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.free.Strings;

import com.bee32.plover.arch.util.OrderComparator;

public class MultiPage {

    Set<MetaPage> index;
    Map<String, MetaPage> map;

    public MultiPage() {
        index = new TreeSet<MetaPage>(OrderComparator.INSTANCE);
        map = new HashMap<String, MetaPage>();
    }

    public void add(String name, Class<?> pageClass) {
        MetaPage metaPage = new MetaPage(pageClass);
        map.put(name, metaPage);
        index.add(metaPage);
    }

    public void add(Class<?> pageClass) {
        String typeName = pageClass.getSimpleName();
        String name = Strings.lcfirst(typeName);
        add(name, pageClass);
    }

}
