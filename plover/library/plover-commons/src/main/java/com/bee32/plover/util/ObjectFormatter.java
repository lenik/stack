package com.bee32.plover.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.UnexpectedException;

import org.springframework.ui.Model;

import com.bee32.plover.arch.Component;

public abstract class ObjectFormatter<T> {

    protected PrettyPrintStream out;
    protected Set<Object> occurred;

    public ObjectFormatter() {
        this.out = new PrettyPrintStream();
    }

    public ObjectFormatter(PrettyPrintStream out, Set<Object> occurred) {
        if (out == null)
            throw new NullPointerException("out");
        this.out = out;
        this.occurred = occurred;
    }

    public synchronized void format(T obj, FormatStyle format, int depth) {
        if (format == null)
            return;

        if (format.isIdentityIncluded())
            formatId(format, obj);

        if (format.isFieldsIncluded()) {
            if (occurred == null)
                occurred = new HashSet<Object>();

            out.print(" {");
            if (format.isMultiLine()) {
                out.println();
                out.enter();
            } else
                out.print(' ');

            formatFields(obj, obj.getClass(), format, depth);

            if (format.isMultiLine())
                out.leave();
            else
                out.print(' ');
            out.print("}");
        }
    }

    protected abstract void formatId(FormatStyle format, T val);

    static final Set<Class<?>> stopClasses = new HashSet<Class<?>>();
    static {
        stopClasses.add(Object.class);
        stopClasses.add(Component.class);
        stopClasses.add(Model.class);
    }

    public static Set<Class<?>> getStopClasses() {
        return stopClasses;
    }

    public static void addStopClass(Class<?> stopClass) {
        stopClasses.add(stopClass);
    }

    void formatFields(Object val, Class<?> clazz, FormatStyle format, int depth) {
        if (stopClasses.contains(clazz))
            return;

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            formatFields(val, superclass, format, depth);

        for (Field field : clazz.getDeclaredFields()) {
            final int skips = Modifier.TRANSIENT | Modifier.STATIC;
            int modifiers = field.getModifiers();
            if ((skips & modifiers) != 0)
                continue;

            // @Skip...

            field.setAccessible(true);
            Object fieldValue;
            try {
                fieldValue = field.get(val);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (fieldValue == null)
                continue;

            // field-name =
            if (format.isNamesIncluded()) {
                String fieldName = field.getName();
                out.print(fieldName);
                if (format.isMultiLine()) // sparse?
                    out.print(" = ");
                else
                    out.print('=');
            }

            try {
                formatValue(fieldValue, format, depth);
            } catch (Exception e) {
                out.print("(error: " + e.getMessage() + ")");
                // e.printStackTrace();
            }

            out.print("; ");
            if (format.isMultiLine())
                out.println();
        } // fields

    } // walk()

    void formatValue(Object val, FormatStyle format, int depth) {
        if (!occurred.add(val)) {
            // duplicated occurence, avoid if acyclic.
            int dupId = System.identityHashCode(val);
            out.print('#');
            out.print(Integer.toHexString(dupId));
            return;
        }

        FormatStyle childFormat;
        int childDepth;
        if (depth < format.getDepth()) {
            childFormat = format;
            childDepth = depth + 1;
        } else {
            childFormat = format.reduce();
            childDepth = 0;
        }

        if (val instanceof IMultiFormat)
            ((IMultiFormat) val).toString(out, childFormat, occurred, childDepth);

        else if (val.getClass().isArray())
            formatArray(val, childFormat, childDepth);

        else if (val instanceof Collection<?>)
            formatCollection((Collection<?>) val, childFormat, childDepth);

        else if (val instanceof Map<?, ?>)
            formatMap((Map<?, ?>) val, childFormat, childDepth);

        else
            out.print(val);
    }

    void formatArray(Object val, FormatStyle format, int depth) {
        int n = Array.getLength(val);
        if (n == 0) {
            out.print("[]");
            return;
        }

        out.print("[");

        if (format.isMultiLine()) {
            out.enter();
            out.println();
        } else
            out.print(' ');

        {
            for (int i = 0; i < n; i++) {
                Object cell = Array.get(val, i);

                formatValue(cell, format, depth);

                out.print(", ");
                if (format.isMultiLine())
                    out.println();
            }
        }

        if (format.isMultiLine())
            out.leave();
        else
            out.print(' ');
        out.print("]");
    }

    static final Class<?> APC_class;
    static final Method APC_isConnectedToSession;
    static {
        try {
            APC_class = Class.forName("org.hibernate.collection.AbstractPersistentCollection");
            APC_isConnectedToSession = APC_class.getDeclaredMethod("isConnectedToSession");
            APC_isConnectedToSession.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new Error("Failed to init APC.isConnectedToSession method.", e);
        }
    }

    void formatCollection(Collection<?> val, FormatStyle format, int depth) {
        if (APC_class.isInstance(val)) {
            try {
                Object connected = APC_isConnectedToSession.invoke(val);
                if (connected != Boolean.TRUE) {
                    out.print("APC/ERROR: Out of session.");
                    return;
                }
            } catch (ReflectiveOperationException e) {
                throw new UnexpectedException(e);
            }
        }

        Collection<?> collection = (Collection<?>) val;
        if (collection.isEmpty()) {
            out.print("()");
            return;
        }

        out.print("(");

        if (format.isMultiLine()) {
            out.enter();
            out.println();
        } else
            out.print(' ');

        {
            for (Object item : collection) {

                formatValue(item, format, depth);

                out.print(", ");
                if (format.isMultiLine())
                    out.println();
            }
        }

        if (format.isMultiLine())
            out.leave();
        else
            out.print(' ');

        out.print(")");
    }

    void formatMap(Map<?, ?> val, FormatStyle format, int depth) {
        Map<?, ?> map = (Map<?, ?>) val;
        if (map.isEmpty()) {
            out.print("{}");
            return;
        }

        out.print("{");

        if (format.isMultiLine()) {
            out.enter();
            out.println();
        } else
            out.print(' ');

        {
            for (Entry<?, ?> entry : map.entrySet()) {

                out.print(entry.getKey());
                out.print(" => ");

                formatValue(entry.getValue(), format, depth);

                out.print(", ");
                if (format.isMultiLine())
                    out.println();
            }
        }

        if (format.isMultiLine())
            out.leave();
        else
            out.print(' ');
        out.print("}");
    }

    @Override
    public String toString() {
        return out.toString();
    }

}