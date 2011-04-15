package com.bee32.plover.arch.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.Assert;

public class ClassUtilTest
        extends Assert {

    class Foo<T> {
    }

    class Bar<S extends Object>
            extends Foo<S> {
    }

    class Bar2<T extends J, J extends K, K extends List<?>>
            extends Bar<T> {
    }

    class Jay
            extends Foo<String> {
    }

    public static void main(String[] args) {
        Type[] originPV = ClassUtil.getOriginPV(Bar2.class);
        Type v1 = originPV[0];

        Class<?> vc1;

        while (v1 instanceof TypeVariable<?>) {
            Type[] bounds = ((TypeVariable<?>) v1).getBounds();
            v1 = bounds[0];
        }

        if (v1 instanceof ParameterizedType)
            v1 = ((ParameterizedType) v1).getRawType();

        vc1 = (Class<?>) v1;
        System.out.println(vc1);
    }

}
