package com.bee32.plover.arch.util;

import static com.bee32.plover.arch.util.ClassUtil.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.Key;
import java.util.EventListener;
import java.util.List;
import java.util.jar.JarFile;

import org.junit.Assert;
import org.junit.Test;

public class ClassUtilTest
        extends Assert {

    class Foo<T> {
    }

    class Bar<S extends Object>
            extends Foo<S> {
    }

    class BarChain<T extends J, J extends K, K extends List<?>>
            extends Bar<T> {
    }

    class BarMix<T extends List<?> & Serializable>
            extends Bar<T> {
    }

    class Jay
            extends Foo<String> {
    }

    interface Iface1<T> {
    }

    interface Iface2<T>
            extends Iface1<T> {
    }

    class Class12<T>
            implements Iface2<T>, Iface1<T> {
    }

    class Class12X
            extends Class12<Long>
            implements EventListener {
    }

    @Test
    public void testBound() {
        Type[] typeArgs = getTypeArgs(R.class, Z.class);
        assertEquals(3, typeArgs.length);

        // interface java.security.Key
        // class java.lang.Long
        // J(class java.util.jar.JarFile)
        Class<Object> v1 = ClassUtil.bound1(typeArgs[0]);
        Class<Object> v2 = ClassUtil.bound1(typeArgs[1]);
        Class<Object> v3 = ClassUtil.bound1(typeArgs[2]);
        assertEquals(Key.class, v1);
        assertEquals(Long.class, v2);
        assertEquals(JarFile.class, v3);
    }

    class Z<T1, T2, T3> {
    }

    class P<S, T>
            extends Z<T, Long, S> {
    }

    class Q<X, Y>
            extends P<Y, X> {
    }

    class R<J extends JarFile>
            extends Q<Key, J> {
    }

    @SuppressWarnings("unused")
    static void handPlay() {

        TypeVariable<?>[] R_decl_args = R.class.getTypeParameters();
        String[] R_decl_bounds = traceBounds(R_decl_args);

        ParameterizedType Rsup = (ParameterizedType) R.class.getGenericSuperclass();
        Type[] Rsup_args = Rsup.getActualTypeArguments();
        String[] Rsup_bounds = traceBounds(Rsup_args);

        Class<?> Qtype = (Class<?>) Rsup.getRawType();
        assert Qtype == Q.class;

        TypeVariable<?>[] Q_decl_args = Qtype.getTypeParameters();
        String[] Q_decl_bounds = traceBounds(Q_decl_args);

        ParameterizedType Qsup = (ParameterizedType) Q.class.getGenericSuperclass();
        Type[] Qsup_args = Qsup.getActualTypeArguments();
        String[] Qsup_bounds = traceBounds(Qsup_args);

        Type[] Rss_args = mapTypeArgs(Q_decl_args, Qsup_args, Rsup_args);
        String[] Rss_bounds = traceBounds(Rss_args);

        Class<?> Ptype = (Class<?>) Qsup.getRawType();
        assert Ptype == P.class;

        TypeVariable<?>[] P_decl_args = Ptype.getTypeParameters();
        String[] P_decl_bounds = traceBounds(P_decl_args);

        ParameterizedType Psup = (ParameterizedType) P.class.getGenericSuperclass();
        Type[] Psup_args = Psup.getActualTypeArguments();
        String[] Psup_bounds = traceBounds(Psup_args);

        Type[] Rsss_args = mapTypeArgs(P_decl_args, Psup_args, Rss_args);
        String[] Rsss_bounds = traceBounds(Rsss_args);
    }

    @Test
    public void testGetTypeName_Type() {
        String typeDisplayName = ClassUtil.getTypeName(ClassUtilTest.class);
        assertEquals("Class Util Test", typeDisplayName);
    }

    @Test
    public void testGetTypeName_LocalType() {
        // *.ClassUtil$Foo.displayName..
        String typeDisplayName = ClassUtil.getTypeName(Foo.class);
        assertEquals("Wonderful Foo!", typeDisplayName);
    }

}
