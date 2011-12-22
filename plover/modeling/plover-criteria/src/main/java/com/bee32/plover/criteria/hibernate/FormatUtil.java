package com.bee32.plover.criteria.hibernate;

import com.bee32.plover.model.Model;

class FormatUtil {

    public static void formatValue(StringBuilder out, Iterable<?> values) {
        int i = 0;
        out.append("[");
        for (Object value : values) {
            if (++i > 1)
                out.append(" ");
            if (value instanceof Model) {
                int hash = System.identityHashCode(value);
                String typeName = value.getClass().getSimpleName();
                String id = Integer.toHexString(hash) + "@" + typeName;
                out.append(id);
            } else {
                // escape..?
                out.append(value);
            }
        }
        out.append("]");
    }

}
