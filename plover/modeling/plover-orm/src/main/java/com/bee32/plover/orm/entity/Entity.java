package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.arch.Component;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

/**
 * You may annotate the concrete entities with:
 * <ul>
 * <li>&#64;Entity
 * <li>&#64;Table
 * </ul>
 */
@MappedSuperclass
public abstract class Entity<K extends Serializable>
        extends EntityBase<K> {

    private static final long serialVersionUID = 1L;

    int version;

    public Entity() {
        super(null);
    }

    public Entity(String name) {
        super(name);
    }

    abstract void setId(K id);

    // @Version
    public int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void populate(Object source) {
    }

    @Override
    public Entity<K> detach() {
        return this;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        @SuppressWarnings("unchecked")
        Entity<K> other = (Entity<K>) obj;

        return equalsEntity(other);
    }

    /**
     * Not used.
     */
    @Override
    protected final boolean equalsSpecific(Component obj) {
        @SuppressWarnings("unchecked")
        Entity<K> other = (Entity<K>) obj;

        return equalsEntity(other);
    }

    protected boolean equalsEntity(Entity<K> other) {
        K id1 = getId();
        K id2 = other.getId();

        if (id1 == null || id2 == null)
            return false;

        if (!id1.equals(id2))
            return false;

        return true;
    }

    @Override
    public final int hashCode() {
        return typeHash + hashCodeEntity();
    }

    /**
     * Not used.
     */
    @Override
    protected final int hashCodeSpecific() {
        int hash = 0xbabade33 * version;

        K id = getId();
        if (id != null)
            hash += id.hashCode();
        else
            hash += hashCodeEntity();

        return hash;
    }

    protected int hashCodeEntity() {
        K id = getId();

        if (id == null)
            return System.identityHashCode(this);
        else
            return id.hashCode();
    }

    @Override
    public final String toString() {
        return toString(FormatStyle.DEFAULT);
    }

    @Override
    public final String toString(FormatStyle format) {
        PrettyPrintStream out = new PrettyPrintStream();
        toString(out, format);
        return out.toString();
    }

    @Override
    public void toString(final PrettyPrintStream out, final FormatStyle format) {
        toString(out, format, null, 0);
    }

    void toString(final PrettyPrintStream out, final FormatStyle format, final Set<Object> occurred, final int depth) {
        if (format == null)
            return;

        // @Fruit:16.3
        if (format.isIdentityIncluded()) {
            String typeName = getClass().getSimpleName();
            if (!typeName.equals(name))
                out.print(name);

            out.print("@");
            out.print(typeName);

            K id = getId();
            if (id != null) {
                out.print(':');
                out.print(id);
                if (version != 0) {
                    out.print('.');
                    out.print(version);
                }
            }
        }

        if (format.isFieldsIncluded()) {
            final int maxDepth = format.getDepth();
            final boolean withNames = format.isNamesIncluded();
            final boolean multiLine = format.isMultiLine();
            final boolean sparse = multiLine;

            out.print(" {");
            if (multiLine) {
                out.println();
                out.enter();
            } else
                out.print(' ');

            {
                class Walk {

                    Set<Object> markSet = occurred;

                    boolean mark(Object o) {
                        if (markSet == null)
                            markSet = new IdentityHashSet();
                        return markSet.add(o);
                    }

                    void walk(Class<?> clazz) {

                        if (clazz == Entity.class)
                            return;

                        Class<?> superclass = clazz.getSuperclass();
                        if (superclass != null)
                            walk(superclass);

                        for (Field field : clazz.getDeclaredFields()) {

                            final int skips = Modifier.TRANSIENT | Modifier.STATIC;
                            int modifiers = field.getModifiers();
                            if ((skips & modifiers) != 0)
                                continue;

                            // @Skip...

                            field.setAccessible(true);
                            Object fieldValue;
                            try {
                                fieldValue = field.get(Entity.this);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }

                            if (fieldValue == null)
                                continue;

                            // field-name =
                            if (withNames) {
                                String fieldName = field.getName();
                                out.print(fieldName);
                                if (sparse)
                                    out.print(" = ");
                                else
                                    out.print('=');
                            }

                            try {
                                walkVal(fieldValue, format, depth);
                            } catch (Exception e) {
                                out.print("(error: " + e.getMessage() + ")");
                                e.printStackTrace();
                            }

                            out.print("; ");
                            if (multiLine)
                                out.println();
                        } // fields

                    } // walk()

                    void walkVal(Object val, FormatStyle format, int depth) {
                        if (!mark(val)) {
                            // duplicated occurence, avoid if acyclic.
                            int dupId = System.identityHashCode(val);
                            out.print('#');
                            out.print(Integer.toHexString(dupId));
                            return;
                        }

                        FormatStyle childFormat;
                        int childDepth;
                        if (depth < maxDepth) {
                            childFormat = format;
                            childDepth = depth + 1;
                        } else {
                            childFormat = format.reduce();
                            childDepth = 0;
                        }

                        if (val instanceof Entity<?>) {
                            Entity<?> o = (Entity<?>) val;
                            {
                                o.toString(out, childFormat, markSet, childDepth);
                            }
                        }

                        else if (val.getClass().isArray()) {
                            int n = Array.getLength(val);
                            if (n == 0) {
                                out.print("[]");
                                return;
                            }

                            out.print("[");

                            if (multiLine) {
                                out.enter();
                                out.println();
                            } else
                                out.print(' ');

                            {
                                for (int i = 0; i < n; i++) {
                                    Object cell = Array.get(val, i);

                                    walkVal(cell, childFormat, childDepth);

                                    out.print(", ");
                                    if (multiLine)
                                        out.println();
                                }
                            }

                            if (multiLine)
                                out.leave();
                            else
                                out.print(' ');
                            out.print("]");
                        }

                        else if (val instanceof Collection<?>) {
                            Collection<?> collection = (Collection<?>) val;
                            if (collection.isEmpty()) {
                                out.print("()");
                                return;
                            }

                            out.print("(");

                            if (multiLine) {
                                out.enter();
                                out.println();
                            } else
                                out.print(' ');

                            {
                                for (Object item : collection) {

                                    walkVal(item, childFormat, childDepth);

                                    out.print(", ");
                                    if (multiLine)
                                        out.println();
                                }
                            }

                            if (multiLine)
                                out.leave();
                            else
                                out.print(' ');

                            out.print(")");
                        }

                        else if (val instanceof Map<?, ?>) {
                            Map<?, ?> map = (Map<?, ?>) val;
                            if (map.isEmpty()) {
                                out.print("{}");
                                return;
                            }

                            out.print("{");

                            if (multiLine) {
                                out.enter();
                                out.println();
                            } else
                                out.print(' ');

                            {
                                for (Entry<?, ?> entry : map.entrySet()) {

                                    out.print(entry.getKey());
                                    out.print(" => ");

                                    walkVal(entry.getValue(), childFormat, childDepth);

                                    out.print(", ");
                                    if (multiLine)
                                        out.println();
                                }
                            }

                            if (multiLine)
                                out.leave();
                            else
                                out.print(' ');
                            out.print("}");

                        }

                        else {
                            out.print(val);
                        }

                    }

                } // class

                new Walk().walk(getClass());
            }

            if (multiLine)
                out.leave();
            else
                out.print(' ');
            out.print("}");
        }
    }

    protected <T extends Entity<K>> T cast(Class<T> thatClass, Entity<K> thatLike) {
        if (thatLike == null)
            return null;

        return thatClass.cast(thatLike);
    }

}
