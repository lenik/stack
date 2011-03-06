package com.bee32.plover.arch.operation;

import java.util.Comparator;

public class OperationDiscovererComparator
        implements Comparator<IOperationDiscoverer> {

    @Override
    public int compare(IOperationDiscoverer o1, IOperationDiscoverer o2) {
        assert o1 != null && o2 != null;
        int p1 = o1.getPriority();
        int p2 = o2.getPriority();
        int cmp = p1 - p2;
        if (cmp != 0)
            return cmp;

        String n1 = o1.getClass().getName();
        String n2 = o2.getClass().getName();
        cmp = n1.compareTo(n2);
        if (cmp != 0)
            return cmp;

        if (o1.equals(o2))
            return 0;
        return -1;
    }

    private static final OperationDiscovererComparator instance = new OperationDiscovererComparator();

    public static OperationDiscovererComparator getInstance() {
        return instance;
    }

}
