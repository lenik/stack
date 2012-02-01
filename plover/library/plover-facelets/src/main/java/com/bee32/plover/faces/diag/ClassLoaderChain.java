package com.bee32.plover.faces.diag;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.free.Caller;

import com.bee32.plover.faces.view.PerView;
import com.bee32.plover.faces.view.ViewBean;

@PerView
public class ClassLoaderChain
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    String start = "SYSTEM";
    String resource;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public ClassLoader getStartLoader() {
        if (start.startsWith("C")) {
            String _level = start.substring(1);
            int level = Integer.parseInt(_level);
            return Caller.getCallerClassLoader(level);
        }

        switch (start) {
        case "SYSTEM":
            return ClassLoader.getSystemClassLoader();
        case "THREAD":
            return Thread.currentThread().getContextClassLoader();
        }
        return null;
    }

    public String getCallerClass() {
        if (start.startsWith("C")) {
            String _level = start.substring(1);
            int level = Integer.parseInt(_level);
            Class<?> clazz = Caller.getCallerClass(level);
            return clazz.getName();
        } else
            return "(n/a)";
    }

    public List<ClassLoaderInfo> getList() {
        List<ClassLoaderInfo> list = new ArrayList<ClassLoaderInfo>();
        ClassLoader loader = getStartLoader();
        while (loader != null) {
            ClassLoaderInfo bean = new ClassLoaderInfo(loader);
            list.add(bean);
            loader = loader.getParent();
        }
        return list;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<URL> getSearchResults()
            throws IOException {
        if (resource == null)
            return Collections.emptyList();

        ClassLoader scl = getStartLoader();
        Enumeration<URL> enm = scl.getResources(resource);
        List<URL> urls = new ArrayList<URL>();
        while (enm.hasMoreElements()) {
            URL url = enm.nextElement();
            urls.add(url);
        }
        return urls;
    }

}
