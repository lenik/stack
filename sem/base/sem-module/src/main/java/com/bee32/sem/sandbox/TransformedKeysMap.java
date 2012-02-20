package com.bee32.sem.sandbox;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.free.IBidiTransformer;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.set.TransformedSet;

public class TransformedKeysMap<_K, K, V>
        extends AbstractMap<K, V> {

    final Map<_K, V> inner;
    IBidiTransformer<K, _K> keyTransformer;

    TransformedKeysMap(Map<_K, V> inner, IBidiTransformer<K, _K> keyTransformer) {
        this.inner = inner;
        this.keyTransformer = keyTransformer;
    }

    public static <_K, K, V> Map<K, V> decorate(Map<_K, V> map, IBidiTransformer<K, _K> keyTransformer) {
        return new TransformedKeysMap<_K, K, V>(map, keyTransformer);
    }

    protected _K getInnerKey(Object outerKey) {
        @SuppressWarnings("unchecked")
        K _outerKey = (K) outerKey;
        return keyTransformer.transform(_outerKey);
    }

    protected K getOuterKey(_K innerKey) {
        return keyTransformer.untransform(innerKey);
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean containsKey(Object outerKey) {
        _K innerKey = getInnerKey(outerKey);
        return inner.containsKey(innerKey);
    }

    @Override
    public V get(Object outerKey) {
        _K innerKey = getInnerKey(outerKey);
        V value = inner.get(innerKey);
        return value;
    }

    @Override
    public V remove(Object outerKey) {
        _K innerKey = getInnerKey(outerKey);
        return inner.remove(innerKey);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<_K> innerKeySet = inner.keySet();
        return TransformedSet.decorate(innerKeySet, new OKeyTransformer());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<_K, V>> innerEntries = inner.entrySet();
        return TransformedSet.decorate(innerEntries, new OEntryTransformer());
    }

    @Override
    public Collection<V> values() {
        return inner.values();
    }

    private class OKeyTransformer
            implements Transformer<_K, K> {
        @Override
        public K transform(_K input) {
            return getOuterKey(input);
        }
    }

    private class OEntryTransformer
            implements Transformer<Entry<_K, V>, Entry<K, V>> {
        @Override
        public Entry<K, V> transform(Entry<_K, V> input) {
            return new OEntry(input);
        }
    }

    private class OEntry
            implements Entry<K, V> {

        Entry<_K, V> innerEntry;

        private OEntry(Entry<_K, V> innerEntry) {
            this.innerEntry = innerEntry;
        }

        @Override
        public K getKey() {
            _K innerKey = innerEntry.getKey();
            K outerKey = getOuterKey(innerKey);
            return outerKey;
        }

        @Override
        public V getValue() {
            return innerEntry.getValue();
        }

        @Override
        public V setValue(V value) {
            return innerEntry.setValue(value);
        }

    }

}
