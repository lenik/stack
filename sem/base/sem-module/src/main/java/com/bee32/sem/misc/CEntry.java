package com.bee32.sem.misc;

import java.util.Map.Entry;

public class CEntry<K, V> {

    final Entry<K, V> orig;

    public CEntry(Entry<K, V> orig) {
        this.orig = orig;
    }

    public <sk> sk getKey() {
        return (sk) orig.getKey();
    }

    public <sv> sv getValue() {
        return (sv) orig.getValue();
    }

    public <sv> sv setValue(V value) {
        return (sv) orig.setValue(value);
    }

}
