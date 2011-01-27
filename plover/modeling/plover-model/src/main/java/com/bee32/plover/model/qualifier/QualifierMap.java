package com.bee32.plover.model.qualifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QualifierMap
        implements IQualified {

    private static final long serialVersionUID = 1L;

    private Map<Class<? extends Qualifier<?>>, List<Qualifier<?>>> map;

    public QualifierMap() {
        map = new TreeMap<Class<? extends Qualifier<?>>, List<Qualifier<?>>>();
    }

    @Override
    public Iterable<? extends Qualifier<?>> getQualifiers() {
        List<Qualifier<?>> all = new ArrayList<Qualifier<?>>();
        for (List<? extends Qualifier<?>> list : map.values()) {
            all.addAll(list);
        }
        return all;
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        @SuppressWarnings("unchecked")
        List<Q> list = (List<Q>) map.get(qualifierType);

        if (list == null)
            return Collections.emptyList();

        return list;
    }

    @Override
    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        return null;
    }

    <Q extends Qualifier<Q>> void addQualifier(Q qualifier, boolean clear) {
        if (qualifier == null)
            throw new NullPointerException("qualifier");

        Class<Q> qualifierType = qualifier.getQualifierType();
        assert qualifierType.equals(qualifier.getClass()) : "Inconsistent qualifier type";

        List<Qualifier<?>> list = map.get(qualifierType);
        if (list == null) {
            list = new ArrayList<Qualifier<?>>();
            map.put(qualifierType, list);
        }

        if (clear)
            list.clear();

        list.add(qualifier);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addQualifiers(Qualifier... qualifiers) {
        for (Qualifier qualifier : qualifiers)
            addQualifier(qualifier);
    }

    public <Q extends Qualifier<Q>> void addQualifier(Q qualifier) {
        addQualifier(qualifier, false);
    }

    public <Q extends Qualifier<Q>> void setQualifier(Q qualifier) {
        addQualifier(qualifier, true);
    }

    public <Q extends Qualifier<Q>> boolean hasQualifier(Class<Q> qualifierType) {
        if (qualifierType == null)
            throw new NullPointerException("qualifierType");
        List<Qualifier<?>> list = map.get(qualifierType);
        if (list == null || list.isEmpty())
            return false;
        return true;
    }

    public <Q extends Qualifier<Q>> void removeQualifiers(Class<Q> qualifierType) {
        map.remove(qualifierType);
    }

}
