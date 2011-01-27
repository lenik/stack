package com.bee32.plover.model.schema;

import java.util.HashMap;
import java.util.Map;

public class MapSchema
        implements ISchema {

    private Map<String, ISchemaElement<?>> map;

    public MapSchema() {
        this.map = new HashMap<String, ISchemaElement<?>>();
    }

    @Override
    public Iterable<String> listNames() {
        return map.keySet();
    }

    @Override
    public Iterable<ISchemaElement<?>> list() {
        return map.values();
    }

    @Override
    public ISchemaElement<?> getElement(String name) {
        return map.get(name);
    }

    public void add(ISchemaElement<?> element) {
        String name = element.getName();
        map.put(name, element);
    }

    public void remove(ISchemaElement<?> element) {
        String name = element.getName();
        map.remove(name);
    }

}
