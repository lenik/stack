package com.bee32.plover.web.faces.diag;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class ClassLoaderChain
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    String resource;

    public List<ClassLoaderInfo> getList() {
        List<ClassLoaderInfo> list = new ArrayList<ClassLoaderInfo>();
        ClassLoader loader = ClassLoader.getSystemClassLoader();
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

    public String getResource2() {
        return "%% " + resource + " %%";
    }

    public List<URL> getSearchResults()
            throws IOException {
        if (resource == null)
            return Collections.emptyList();

        ClassLoader scl = ClassLoader.getSystemClassLoader();
        Enumeration<URL> enm = scl.getResources(resource);
        List<URL> urls = new ArrayList<URL>();
        while (enm.hasMoreElements()) {
            URL url = enm.nextElement();
            urls.add(url);
        }
        return urls;
    }

}
