package com.bee32.plover.ox1.c;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;

public class KeywordBuilder
        extends AbstractCollection<String>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    StringBuilder buffer = new StringBuilder();

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String e) {
        if (e != null && !e.isEmpty())
            buffer.append(e);
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

}
