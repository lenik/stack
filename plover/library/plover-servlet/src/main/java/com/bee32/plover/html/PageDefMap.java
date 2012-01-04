package com.bee32.plover.html;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.free.Strings;

import com.bee32.plover.site.IPageGenerator;

public class PageDefMap {

    List<IPageGenerator> index;
    Map<String, IPageGenerator> pageMap;

    public PageDefMap() {
        index = new ArrayList<IPageGenerator>(); // (OrderComparator.INSTANCE);
        pageMap = new LinkedHashMap<String, IPageGenerator>();
    }

    public void add(String name, Class<? extends AbstractHtmlTemplate> pageClass) {
        InstantiatePageGenerator page = new InstantiatePageGenerator(pageClass);
        index.add(page);
        pageMap.put(name, page);
    }

    public void add(Class<? extends AbstractHtmlTemplate> pageClass) {
        String typeName = pageClass.getSimpleName();
        String name = Strings.lcfirst(typeName);
        add(name, pageClass);
    }

    public void add(String name, int order, Object content) {
        IPageGenerator page = new SimplePageGenerator(order, content);
        index.add(page);
        pageMap.put(name, page);
    }

    public IPageGenerator getPage(String name) {
        IPageGenerator page = pageMap.get(name);
        return page;
    }

    public Map<String, IPageGenerator> getPageMap() {
        return pageMap;
    }

}
