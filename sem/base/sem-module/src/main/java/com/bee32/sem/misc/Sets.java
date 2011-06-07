package com.bee32.sem.misc;

import java.util.HashSet;
import java.util.Set;

public class Sets {

    /**
     * Create a new hash set from the provided elements.
     *
     * @return Non-<code>null</code> {@link Set}.
     */
    public static <T> Set<T> newSet(T... elements) {
        Set<T> set = new HashSet<T>();
        for (T element : elements)
            set.add((T) element);
        return set;
    }

}
