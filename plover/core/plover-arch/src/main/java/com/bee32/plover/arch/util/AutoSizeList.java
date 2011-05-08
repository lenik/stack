package com.bee32.plover.arch.util;

import java.util.ArrayList;

public class AutoSizeList<T>
        extends ArrayList<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public T get(int index) {
        if (index >= size())
            return null;
        else
            return super.get(index);
    }

    /**
     * Expand the list if index is out of range.
     */
    @Override
    public T set(int index, T element) {
        int require = (index + 1) - size();

        while (require-- > 0)
            add(null);

        return super.set(index, element);
    }

}
