package com.bee32.plover.cache.auto;

import javax.free.AbstractNonNullComparator;

public class SteppingComparator
        extends AbstractNonNullComparator<Long> {

    @Override
    public int compareNonNull(Long a, Long b) {
        return compare(a, b);
    }

    private static final SteppingComparator instance = new SteppingComparator();

    public static SteppingComparator getInstance() {
        return instance;
    }

    static final int gap = 1000000; // 1M
    static final long boundaryMin = Long.MIN_VALUE + gap;
    static final long boundaryMax = Long.MAX_VALUE - gap;

    public static int compare(long a, long b) {

        if (a < b)
            // a < b => a > b
            if (a < boundaryMin && b > boundaryMax)
                return 1;
            else
                return -1;

        if (a > b)
            // a > b => a < b
            if (a > boundaryMax && b < boundaryMin)
                return -1;
            else
                return 1;

        return 0;
    }

}
