package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.free.ProxyMap;

public class CastLinkedMap<K, V>
        extends ProxyMap<K, V>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public CastLinkedMap(Map<K, V> proxy) {
        super(proxy);
    }

    // @Override
    // public <sk extends K> Set<sk> keySet() {
    // return (Set<sk>) super.keySet();
    // }
    //
    // @Override
    // public <sv extends V> Collection<sv> values() {
    // return (Collection<sv>) super.values();
    // }

    public <sk extends K> Set<sk> _keySet() {
        return (Set<sk>) super.keySet();
    }

    public <sv extends V> Collection<sv> _values() {
        return (Collection<sv>) values();
    }

    public <sv extends V> sv _get(Object key) {
        return (sv) super.get(key);
    }

}
