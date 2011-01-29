package com.bee32.plover.restful.oid;

import java.util.Comparator;

public class OidComparator
        implements Comparator<OidVector> {

    @Override
    public int compare(OidVector o1, OidVector o2) {
        if (o1 == null)
            return o2 == null ? 0 : -1;
        else if (o2 == null)
            return 1;

        int[] v1 = o1.vector;
        int[] v2 = o2.vector;
        int minlen = v1.length <= v2.length ? v1.length : v2.length;
        for (int i = 0; i < minlen; i++) {
            int cmp = v1[i] - v2[i];
            if (cmp != 0)
                return cmp;
        }

        return v1.length - v2.length;
    }

    private static final OidComparator instance = new OidComparator();

    public static OidComparator getInstance() {
        return instance;
    }

}
