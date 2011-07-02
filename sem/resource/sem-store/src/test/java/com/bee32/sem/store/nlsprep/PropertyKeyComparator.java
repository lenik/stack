package com.bee32.sem.store.nlsprep;

import java.util.HashMap;
import java.util.Map;

import javax.free.AbstractNonNullComparator;

public class PropertyKeyComparator
        extends AbstractNonNullComparator<String> {

    static final String[] _leaves = {
            // For project.*
            "groupId", //
            "artifactId", //
            "version", //

            // For appearance.*
            "label", //
            "description", //
    };
    static final Map<String, Integer> leafOrder;
    static {
        leafOrder = new HashMap<String, Integer>();
        for (int i = 0; i < _leaves.length; i++)
            leafOrder.put(_leaves[i], -1000 + i);
    }

    @Override
    public int compareNonNull(String o1, String o2) {
        int ldot1 = o1.lastIndexOf('.');
        int ldot2 = o2.lastIndexOf('.');
        if (ldot1 == -1)
            return -1;
        if (ldot2 == -1)
            return 1;

        String prefix1 = o1.substring(0, ldot1);
        String prefix2 = o2.substring(0, ldot2);
        int cmp = prefix1.compareTo(prefix2);
        if (cmp != 0)
            return cmp;

        String suffix1 = o1.substring(ldot1);
        String suffix2 = o2.substring(ldot2);
        Integer sl1 = leafOrder.get(suffix1);
        Integer sl2 = leafOrder.get(suffix2);
        if (sl1 == null)
            sl1 = 0;
        if (sl2 == null)
            sl2 = 0;

        cmp = sl1 - sl2;
        if (cmp != 0)
            return cmp;

        cmp = suffix1.compareTo(suffix2);
        return cmp;
    }

    public static final PropertyKeyComparator INSTANCE = new PropertyKeyComparator();

}
