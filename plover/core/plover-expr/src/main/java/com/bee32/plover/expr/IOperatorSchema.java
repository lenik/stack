package com.bee32.plover.expr;

import java.util.Map;

public interface IOperatorSchema {

    Object add(Object o1, Object o2);

    Object subtract(Object o1, Object o2);

    Object multiply(Object o1, Object o2);

    Object divide(Object o1, Object o2);

    Object sum(Object... args);

    Object avg(Object... args);

    Object mod(Object o1, Object o2);

    Object negative(Object o);

    Object conj(Object o1, Object o2);

    Object disj(Object o1, Object o2);

    boolean equals(Object o1, Object o2);

    boolean lessThan(Object o1, Object o2);

    boolean greaterThan(Object o1, Object o2);

    boolean lessOrEquals(Object o1, Object o2);

    boolean greaterOrEquals(Object o1, Object o2);

    boolean like(Object o, String pattern);

    boolean toBoolean(Object o);

    Object invoke(String methodName, Object obj, Object... args);

    Object subscript(Object o, int index);

    Object subscript(Object o, Object key);

    Map<String, ?> deref(Object o);

}
