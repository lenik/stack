package com.bee32.plover.arch.operation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class OperationBuilder {

    private final Map<String, IOperation> map;

    public OperationBuilder() {
        map = new TreeMap<String, IOperation>();
    }

    public void add(IOperation operation) {
        if (operation == null)
            throw new NullPointerException("operation");
        map.put(operation.getName(), operation);
    }

    public void discover(Class<?> clazz) {
        OperationFusion fusion = OperationFusion.getInstance();
        Map<String, IOperation> operations = fusion.getTypeOperations(clazz);
        if (operations != null)
            map.putAll(operations);
    }

    public void discover(Object instance) {
        OperationFusion fusion = OperationFusion.getInstance();
        Map<String, IOperation> operations = fusion.getInstanceOperations(instance);
        if (operations != null)
            map.putAll(operations);
    }

    public void remove(String operationName) {
        map.remove(operationName);
    }

    public IOperation get(String operationName) {
        return map.get(operationName);
    }

    public Map<String, IOperation> getMap() {
        return map;
    }

    public Set<String> getOperationNames() {
        return map.keySet();
    }

    public Collection<IOperation> getOperations() {
        return map.values();
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
        OperationBuilder other = (OperationBuilder) obj;
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
