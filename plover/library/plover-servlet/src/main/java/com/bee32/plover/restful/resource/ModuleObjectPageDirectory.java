package com.bee32.plover.restful.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;

public class ModuleObjectPageDirectory
        extends AbstractObjectPageDirectory {

    IModule module;
    String oidPath;
    Location moduleLocation;

    Map<String, List<String>> localViewPagesMap = new HashMap<String, List<String>>();
    Map<String, List<String>> localOperationPagesMap = new HashMap<String, List<String>>();

    public ModuleObjectPageDirectory(IModule module) {
        if (module == null)
            throw new NullPointerException("module");
        oidPath = OidUtil.getOid(module.getClass()).toPath() + "/";
        moduleLocation = Locations.WEB_APP.join(oidPath);
    }

    @Override
    public Location getBaseLocation() {
        Location location = moduleLocation;
        String baseHref = getBaseHref();
        if (baseHref != null)
            location = location.join(baseHref);
        return location;
    }

    protected String getBaseHref() {
        return null;
    }

    protected List<Location> expand(Collection<String> localPages) {
        List<Location> locations = new ArrayList<Location>(localPages.size());
        for (String localPage : localPages)
            locations.add(expand(localPage));
        return locations;
    }

    protected Location expand(String localPage) {
        Location location = getBaseLocation().join(localPage);
        return location;
    }

    @Override
    public Set<String> getViewNames() {
        return localViewPagesMap.keySet();
    }

    @Override
    public Set<String> getOperationNames() {
        return localOperationPagesMap.keySet();
    }

    @Override
    public List<Location> getPagesForView(String viewName, Map<String, ?> parameters) {
        Collection<String> localPages = getLocalPagesForView(viewName, parameters);
        return expand(localPages);
    }

    @Override
    public List<Location> getPagesForOperation(String operationName, Map<String, ?> parameters) {
        Collection<String> localPages = getLocalPagesForOperation(operationName, parameters);
        return expand(localPages);
    }

    protected synchronized List<String> getLocalViewPages(String viewName) {
        List<String> list = localViewPagesMap.get(viewName);
        if (list == null)
            localViewPagesMap.put(viewName, list = new ArrayList<String>());
        return list;
    }

    protected synchronized List<String> getLocalOperationPages(String operationName) {
        List<String> list = localOperationPagesMap.get(operationName);
        if (list == null)
            localOperationPagesMap.put(operationName, list = new ArrayList<String>());
        return list;
    }

    public synchronized void addLocalPageForView(String viewName, String localPage) {
        getLocalViewPages(viewName).add(localPage);
    }

    public void removeLocalPagesForView(String viewName) {
        localViewPagesMap.remove(viewName);
    }

    public void addLocalPageForOperation(String operationName, String localPage) {
        getLocalOperationPages(operationName).add(localPage);
    }

    public void removeLocalPagesForOperation(String operationName) {
        localOperationPagesMap.remove(operationName);
    }

    protected List<String> getLocalPagesForView(String viewName, Map<String, ?> parameters) {
        if (viewName == null)
            throw new NullPointerException("viewName");
        Collection<String> localPages = getLocalViewPages(viewName);
        return formatHref(localPages, parameters);
    }

    protected List<String> getLocalPagesForOperation(String operationName, Map<String, ?> parameters) {
        if (operationName == null)
            throw new NullPointerException("operationName");
        Collection<String> localPages = getLocalOperationPages(operationName);
        return formatHref(localPages, parameters);
    }

    protected List<String> formatHref(Collection<String> paths, Map<String, ?> parameters) {
        List<String> formattedPaths = new ArrayList<String>(paths.size());
        for (String path : paths) {
            formattedPaths.add(formatHref(path, parameters));
        }
        return formattedPaths;
    }

    protected String formatHref(String path, Map<String, ?> parameters) {
        if (path == null)
            return null;
        if (parameters == null)
            return path;

        StringBuilder buf = new StringBuilder(path.length() + parameters.size() * 32);
        buf.append(path);

        boolean firstParam = path.lastIndexOf('?') == -1;

        for (Entry<String, ?> parameter : parameters.entrySet()) {
            if (firstParam) {
                buf.append("?");
                firstParam = false;
            } else {
                buf.append("&");
            }

            String paramName = parameter.getKey();
            Object paramValue = parameter.getValue();
            if (paramValue == null)
                continue;
            buf.append(paramName);
            buf.append("=");
            buf.append(encodeURI(paramValue));
        }
        return buf.toString();
    }

    static final String encoding = "utf-8";

    protected String encodeURI(Object value) {
        String str = String.valueOf(value);
        try {
            String encoded = URLEncoder.encode(str, encoding);
            return encoded;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
