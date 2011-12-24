package com.bee32.plover.restful.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;

public class ModuleObjectPageDirectory
        extends AbstractObjectPageDirectory {

    Module module;
    String prefix;
    Location moduleLocation;

    Map<String, String> localViewPages = new HashMap<String, String>();
    Map<String, String> localOperationPages = new HashMap<String, String>();

    public ModuleObjectPageDirectory(Module module) {
        if (module == null)
            throw new NullPointerException("module");
        prefix = OidUtil.getOid(module.getClass()).toPath() + "/";
        moduleLocation = Locations.WEB_APP.join(prefix);
    }

    @Override
    public Location getBaseLocation() {
        return moduleLocation;
    }

    protected Location expand(String localPage) {
        Location location = getBaseLocation().join(localPage);
        return location;
    }

    @Override
    public Collection<String> getPagesForView() {
        return localViewPages.keySet();
    }

    @Override
    public Collection<String> getPagesForOperation() {
        return localOperationPages.keySet();
    }

    @Override
    public Location getPageForView(String viewName, Map<String, ?> parameters) {
        String localPage = getLocalPageForView(viewName, parameters);
        return expand(localPage);
    }

    @Override
    public Location getPageForOperation(String operationName, Map<String, ?> parameters) {
        String localPage = getLocalPageForOperation(operationName, parameters);
        return expand(localPage);
    }

    public void addLocalPageForView(String viewName, String localPage) {
        localViewPages.put(viewName, localPage);
    }

    public String removeLocalPageForView(String viewName) {
        return localViewPages.remove(viewName);
    }

    public void addLocalPageForOperation(String operationName, String localPage) {
        localOperationPages.put(operationName, localPage);
    }

    public String removeLocalPageForOperation(String operationName) {
        return localOperationPages.remove(operationName);
    }

    protected String getLocalPageForView(String viewName, Map<String, ?> parameters) {
        if (viewName == null)
            throw new NullPointerException("viewName");
        String localPage = localViewPages.get(viewName);
        return formatHref(localPage, parameters);
    }

    protected String getLocalPageForOperation(String operationName, Map<String, ?> parameters) {
        if (operationName == null)
            throw new NullPointerException("operationName");
        String localPage = localOperationPages.get(operationName);
        return formatHref(localPage, parameters);
    }

    protected String formatHref(String path, Map<String, ?> parameters) {
        if (path == null)
            return null;
        if (parameters == null)
            return path;
        StringBuilder buf = new StringBuilder(path.length() + parameters.size() * 32);
        buf.append(path);
        int i = 0;
        for (Entry<String, ?> parameter : parameters.entrySet()) {
            if (i++ == 0)
                buf.append("?");
            else
                buf.append("&");
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
