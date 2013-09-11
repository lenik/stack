package com.bee32.plover.servlet.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.collections15.iterators.IteratorEnumeration;

/**
 * NOTICE: Please use request attributes instead of parameters.
 */
public class ModifiableHttpServletRequest
        extends HttpServletRequestWrapper {

    private Map<String, String[]> copy;
    private String pathInfo;

    public ModifiableHttpServletRequest(HttpServletRequest proxy) {
        super(proxy);

        /**
         * The map returned by getParameterMap() is immutable.
         */
        Map<String, String[]> origMap = proxy.getParameterMap();

        copy = new TreeMap<String, String[]>(origMap);
        pathInfo = proxy.getPathInfo();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return copy;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> names = copy.keySet();
        return new IteratorEnumeration<String>(names.iterator());
    }

    @Override
    public String getParameter(String name) {
        String[] values = copy.get(name);
        if (values == null)
            return null;
        return values[0];
    }

    public void setParameter(String name, String value) {
        String[] values = new String[] { value };
        copy.put(name, values);
    }

    @Override
    public String[] getParameterValues(String name) {
        return copy.get(name);
    }

    public void setParameterValues(String name, String[] values) {
        copy.put(name, values);
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, String[]> entry : copy.entrySet()) {
            buf.append(entry.getKey());
            buf.append(" = ");
            boolean inited = false;
            for (String value : entry.getValue()) {
                if (!inited)
                    inited = true;
                else
                    buf.append(", ");
                buf.append(value);
            }
            buf.append('\n');
        }
        return buf.toString();
    }

}
