package com.bee32.plover.arch.operation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.inject.AbstractContainer;

public class OperationContext
        extends AbstractContainer
        implements IOperationContext {

    private String path;

    private Map<String, Object> map;

    private IResultOutput resultOutput = LoggerResultOutput.getInstance();

    private Object returnValue;

    public OperationContext() {
        map = new TreeMap<String, Object>();
    }

    public OperationContext(Map<String, Object> map) {
        if (map == null)
            throw new NullPointerException("map");
        this.map = map;
    }

    public OperationContext(Object... parameters) {
        for (int i = 0; i < parameters.length; i++)
            put(String.valueOf(i), parameters[i]);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public IResultOutput getResultOutput() {
        return resultOutput;
    }

    public void setResultOutput(IResultOutput resultOutput) {
        this.resultOutput = resultOutput;
    }

    @Override
    public Object get(int parameterIndex) {
        return get(String.valueOf(parameterIndex));
    }

    @Override
    public <T> T getScalar(String key) {
        Object value = get(key);
        return MapStruct.toScalar(value);
    }

    @Override
    public <T> T[] getArray(String key) {
        Object value = get(key);
        return MapStruct.toArray(value);
    }

    // Map delegates

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public Object remove(Object key) {
        return map.remove(key);
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        map.putAll(m);
    }

    public void clear() {
        map.clear();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public Collection<Object> values() {
        return map.values();
    }

    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

}
