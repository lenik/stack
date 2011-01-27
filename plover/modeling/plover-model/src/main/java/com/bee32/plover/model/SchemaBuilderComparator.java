package com.bee32.plover.model;

import java.util.Comparator;

public class SchemaBuilderComparator
        implements Comparator<SchemaBuilder> {

    @Override
    public int compare(SchemaBuilder o1, SchemaBuilder o2) {
        if (o1 == null)
            if (o2 == null)
                return 0;
            else
                return -1;
        if (o2 == null)
            return 1;

        return o1.getPriority() - o2.getPriority();
    }

    private static final SchemaBuilderComparator instance = new SchemaBuilderComparator();

    public static SchemaBuilderComparator getInstance() {
        return instance;
    }

}
