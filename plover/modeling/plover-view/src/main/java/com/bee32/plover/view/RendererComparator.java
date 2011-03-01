package com.bee32.plover.view;

import java.util.Comparator;

public class RendererComparator
        implements Comparator<IContentRenderer> {

    @Override
    public int compare(IContentRenderer o1, IContentRenderer o2) {
        int cmp = o1.getPriority() - o2.getPriority();
        if (cmp != 0)
            return cmp;

        String n1 = o1.getClass().getName();
        String n2 = o2.getClass().getName();
        cmp = n1.compareTo(n2);
        return cmp;
    }

    private static final RendererComparator instance = new RendererComparator();

    public static RendererComparator getInstance() {
        return instance;
    }

}
