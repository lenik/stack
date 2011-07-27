package com.bee32.plover.criteria.hibernate;

class CompareUtil {

    public static int compare(Object lhs, Object rhs) {
        @SuppressWarnings("unchecked")
        Comparable<Object> _lhs = (Comparable<Object>) lhs;
        int cmp = _lhs.compareTo(rhs);
        return cmp;
    }

}
