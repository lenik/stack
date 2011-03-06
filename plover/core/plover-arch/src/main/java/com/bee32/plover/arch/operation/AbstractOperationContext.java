package com.bee32.plover.arch.operation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.bee32.plover.inject.AbstractContainer;

public abstract class AbstractOperationContext
        extends AbstractContainer
        implements IOperationContext {

    private static final long serialVersionUID = 1L;

    private String path;

    private Map<String, Object> map = new TreeMap<String, Object>();

    private IResultOutput resultOutput = LoggerResultOutput.getInstance();

    public AbstractOperationContext() {
        super();
    }

    public AbstractOperationContext(String name) {
        super(name);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public IResultOutput getResultOutput() {
        return resultOutput;
    }

    public void setResultOutput(IResultOutput resultOutput) {
        this.resultOutput = resultOutput;
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

    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

}
