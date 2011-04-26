package com.bee32.icsf.principal.dto;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Assert;

import com.bee32.plover.util.PrettyPrintStream;

public class PrincipalDtoTest
        extends Assert {

    static String dumpGenericTree(Type type) {
        PrettyPrintStream out = new PrettyPrintStream();
        dumpGenericTree(out, type);
        return out.toString();
    }

    static void dumpGenericTree(PrettyPrintStream out, Type type) {
        out.println(type);

        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;

            Type owner = ptype.getOwnerType();
            if (owner != null)
                out.println("  owner = " + owner);

            Type rawType = ptype.getRawType();
            if (rawType != null)
                out.println("  rawType = " + rawType);

            Type[] tv = ptype.getActualTypeArguments();
            if (tv.length != 0) {
                out.println("  actualTypeArguments:");
                out.enter();
                for (Type typeArg : tv) {
                    out.println(typeArg);
                }
                out.leave();
            }

            type = rawType;
        }

        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;

            Type[] ifaces = clazz.getGenericInterfaces();
            if (ifaces.length != 0) {
                out.println("  Generic interfaces: ");
                out.enter();
                for (Type iface : ifaces)
                    dumpGenericTree(out, iface);
                out.leave();
            }

            Type superclass = clazz.getGenericSuperclass();
            if (superclass != null) {
                out.enter();
                dumpGenericTree(out, superclass);
                out.leave();
            }
        }

    }

}
