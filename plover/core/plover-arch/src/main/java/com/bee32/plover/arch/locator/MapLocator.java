package com.bee32.plover.arch.locator;

import java.io.Serializable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapLocator<T>
        extends AbstractObjectLocator
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, T> map = new TreeMap<String, T>();
    private final Map<T, String> reverseMap = new IdentityHashMap<T, String>();

    public MapLocator(Class<?> baseType) {
        super(baseType, null);
    }

    public MapLocator(IObjectLocator parent) {
        super(null, parent);
    }

    public MapLocator(Class<?> baseType, IObjectLocator parent) {
        super(baseType, parent);
    }

    @Override
    public Object locate(String locationToken) {
        return map.get(locationToken);
    }

    @Override
    public boolean isLocatable(Object obj) {
        return reverseMap.containsKey(obj);
    }

    @Override
    public String getLocation(Object obj) {
        return reverseMap.get(obj);
    }

    public void setLocation(String locationToken, T obj) {
        map.put(locationToken, obj);
        reverseMap.put(obj, locationToken);
    }

    public void remove(Object obj) {
        String locationToken = reverseMap.remove(obj);
        map.remove(locationToken);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String locationToken = entry.getKey();
            T obj = entry.getValue();

            buf.append(locationToken);
            buf.append(" => ");
            buf.append(String.valueOf(obj));
            buf.append('\n');
        }
        return buf.toString();
    }

}
