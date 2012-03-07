package com.bee32.plover.orm.sample;

import javax.free.AbstractNonNullComparator;

public class SamplePackageComparator
        extends AbstractNonNullComparator<SamplePackage> {

    @Override
    public int compareNonNull(SamplePackage o1, SamplePackage o2) {
        int level1 = o1.getLevel();
        int level2 = o2.getLevel();
        if (level1 != level2)
            return level1 - level2;

        int priority1 = o1.getPriority();
        int priority2 = o2.getPriority();
        if (priority1 != priority2)
            return priority1 - priority2;

        int seq1 = o1.getSeq();
        int seq2 = o2.getSeq();
        if (seq1 != seq2)
            return seq1 - seq2;

        return -1;
    }

    public static final SamplePackageComparator INSTANCE = new SamplePackageComparator();

}
