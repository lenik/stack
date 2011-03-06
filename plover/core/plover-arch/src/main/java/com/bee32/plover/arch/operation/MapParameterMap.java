package com.bee32.plover.arch.operation;

import java.io.Serializable;
import java.util.Map;

public class MapParameterMap
        implements IParameterMap, Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<?, ?> map;

    public MapParameterMap(Map<?, ?> map) {
        if (map == null)
            throw new NullPointerException("map");
        this.map = map;
    }

    @Override
    public Object get(int parameterIndex) {
        return get(String.valueOf(parameterIndex));
    }

    @Override
    public Object get(Object parameterKey) {
        return map.get(parameterKey);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapParameterMap other = (MapParameterMap) obj;
        if (map == null) {
            if (other.map != null)
                return false;
        } else if (!map.equals(other.map))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
