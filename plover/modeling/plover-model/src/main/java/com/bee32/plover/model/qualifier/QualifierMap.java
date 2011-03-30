package com.bee32.plover.model.qualifier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class QualifierMap
        implements IQualified {

    private static final long serialVersionUID = 1L;

    private Map<Class<? extends Qualifier<?>>, TreeSet<Qualifier<?>>> map;

    public QualifierMap() {
    }

    public Map<Class<? extends Qualifier<?>>, TreeSet<Qualifier<?>>> getMap() {
        if (map == null) {
            synchronized (this) {
                if (map == null) {
                    map = new HashMap<Class<? extends Qualifier<?>>, TreeSet<Qualifier<?>>>();
                }
            }
        }
        return map;
    }

    @Override
    public Iterable<Qualifier<?>> getQualifiers() {
        TreeSet<Qualifier<?>> all = new TreeSet<Qualifier<?>>(QualifierComparator.getInstance());
        for (Collection<? extends Qualifier<?>> set : getMap().values()) {
            all.addAll(set);
        }
        return all;
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        Collection<Q> set = (Collection<Q>) getMap().get(qualifierType);

        if (set == null)
            return Collections.emptyList();

        return set;
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

        TreeSet<Qualifier<?>> set = getMap().get(qualifierType);
        if (set == null) {
            set = new TreeSet<Qualifier<?>>(QualifierComparator.getInstance());
            getMap().put(qualifierType, set);
        }

        if (clear)
            set.clear();

        set.add(qualifier);
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
        TreeSet<Qualifier<?>> set = getMap().get(qualifierType);
        if (set == null || set.isEmpty())
            return false;
        return true;
    }

    public <Q extends Qualifier<Q>> void removeQualifiers(Class<Q> qualifierType) {
        getMap().remove(qualifierType);
    }

}
