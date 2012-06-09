package com.bee32.plover.site.cfg;

import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.Nullables;

import com.bee32.plover.arch.util.ILabelledEntry;

public class Relabel<K>
        implements Entry<K, String>, ILabelledEntry {

    K key;
    String label;

    public Relabel(K key, String label) {
        this.key = key;
        this.label = label;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return label;
    }

    @Override
    public String setValue(String value) {
        String old = this.label;
        this.label = value;
        return old;
    }

    @Override
    public String getEntryLabel() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != Relabel.class)
            return false;
        Relabel<?> o = (Relabel<?>) obj;
        if (!Nullables.equals(key, o.key))
            return false;
        // if (!Nullables.equals(label, o.label))
        // return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int hash = 0;
        hash = hash * prime + (key == null ? 0 : key.hashCode());
        // hash = hash * prime + (label == null ? 0 : label.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return getEntryLabel();
    }

    /**
     * Create Relabel-wrappers for the key set.
     */
    public static <K> Set<Relabel<K>> convert(Set<K> keys) {
        Set<Relabel<K>> relabels = new LinkedHashSet<Relabel<K>>();
        for (K key : keys) {
            if (key == null)
                throw new NullPointerException("key");
            Relabel<K> relabel = new Relabel<K>(key, _label(key));
            relabels.add(relabel);
        }
        return relabels;
    }

    static String _label(Object o) {
        if (o == null)
            return null;
        if (o instanceof ILabelledEntry)
            return ((ILabelledEntry) o).getEntryLabel();
        return o.toString();
    }

}
