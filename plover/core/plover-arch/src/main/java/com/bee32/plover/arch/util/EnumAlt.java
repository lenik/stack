package com.bee32.plover.arch.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.arch.util.res.ResourceBundleUTF8;
import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;

/**
 * Alternative enumeration support.
 *
 * <p>
 * Code template for eclipse:
 *
 * <pre>
 * class ${EnumType}
 *         extends EnumAlt<${ValType}, ${EnumType}> {
 *
 *     private static final long serialVersionUID = 1L;
 *
 *     static final Map<String, ${EnumType}> nameMap = new HashMap<String, ${EnumType}>();
 *     static final Map<${ValType}, ${EnumType}> valueMap = new HashMap<${ValType}, ${EnumType}>();
 *
 *     public static ${EnumType} forName(String altName) {
 *         ${EnumType} ${typeName:localVar} = nameMap.get(altName);
 *         if (${typeName:localVar} == null)
 *             throw new NoSuchEnumException(${EnumType}.class, altName);
 *         return ${typeName:localVar};
 *     }
 *
 *     public static Collection<${EnumType}> values() {
 *         Collection<${EnumType}> values = valueMap.values();
 *         return Collections.unmodifiableCollection(values);
 *     }
 *
 *     public static ${EnumType} valueOf(${ValType} value) {
 *         if (value == null)
 *             return null;
 *
 *         ${EnumType} ${typeName:localVar} = valueMap.get(value);
 *         if (${typeName:localVar} == null)
 *             throw new NoSuchEnumException(${EnumType}.class, value);
 *
 *         return ${typeName:localVar};
 *     }
 *
 * }
 * </pre>
 */
@ServiceTemplate(prototype = true)
public abstract class EnumAlt<V extends Serializable, $ extends EnumAlt<V, $>>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final V value;
    protected final String name;
    protected final String label;
    protected final String description;
    protected final Location icon;

    public EnumAlt(V value, String name) {
        if (name == null)
            throw new NullPointerException("name");
        if (value == null)
            throw new NullPointerException("value");

        this.name = name;
        this.value = value;

        label = _nls(name + ".label", name);
        description = _nls(name + ".description", null);

        String icon = _nls(name + ".icon", null);
        if (icon != null)
            this.icon = Locations.parse(icon);
        else
            this.icon = null;
    }

    /**
     * Get the name map of this enumeration type.
     *
     * @return A non-<code>null</code> map contains all the enum elements.
     */
    protected final Map<String, $> getNameMap() {
        @SuppressWarnings("unchecked")
        Map<String, $> nameMap = (Map<String, $>) getNameMap(getClass());
        return nameMap;
    }

    /**
     * Get the value map of this enumeration type.
     *
     * @return A non-<code>null</code> map contains all the enum elements.
     */
    protected final Map<V, $> getValueMap() {
        @SuppressWarnings("unchecked")
        Map<V, $> valueMap = (Map<V, $>) getValueMap(getClass());
        return valueMap;
    }

    public String getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getDisplayName() {
        if (label != null && !label.isEmpty())
            return label;
        else
            return name;
    }

    public String getDescription() {
        return description;
    }

    public Location getIcon() {
        return icon;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EnumAlt<?, ?>))
            return false;
        EnumAlt<?, ?> o = (EnumAlt<?, ?>) obj;
        return o.value.equals(value);
    }

    @Override
    public String toString() {
        return label;
    }

    protected String _nls(String key, String def) {
        Locale locale = Locale.getDefault(); // SystemCLG.locale.get();
        String baseName = getClass().getName();

        ResourceBundle rb;
        try {
            rb = ResourceBundleUTF8.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            return def;
        }

        if (rb.containsKey(key))
            return rb.getString(key);
        else
            return def;
    }

    static <E extends EnumAlt<V, E>, V extends Serializable> //
    void scanEnumTypes(boolean publicOnly)
            throws ClassNotFoundException, IOException {
        for (Class<?> enumType : ServicePrototypeLoader.load(EnumAlt.class)) {
            for (Field field : enumType.getDeclaredFields()) {
                // public static ...
                int mod = field.getModifiers();
                if (!Modifier.isStatic(mod))
                    continue;
                if (!Modifier.isPublic(mod))
                    if (publicOnly)
                        continue;
                    else
                        field.setAccessible(true);

                Class<E> declareType = (Class<E>) field.getType();
                if (!enumType.isAssignableFrom(declareType))
                    continue;

                E entry;
                try {
                    entry = (E) field.get(null);
                } catch (ReflectiveOperationException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }
                // Class<E> actualType = (Class<E>) entry.getClass();

                registerTree(declareType, entry);
            }
        }
    }

    static final ClassLocal<Map<String, ? extends EnumAlt<?, ?>>> clNameMap = new ClassLocal<>();
    static final ClassLocal<Map<Object, ? extends EnumAlt<?, ?>>> clValueMap = new ClassLocal<>();

    protected static synchronized <E extends EnumAlt<?, E>> //
    Map<String, E> getNameMap(Class<E> enumType) {
        Map<String, E> nameMap = (Map<String, E>) clNameMap.get(enumType);
        if (nameMap == null) {
            nameMap = new LinkedHashMap<>();
            clNameMap.put(enumType, nameMap);
        }
        return nameMap;
    }

    protected static synchronized <E extends EnumAlt<V, E>, V extends Serializable> //
    Map<V, E> getValueMap(Class<E> enumType) {
        Map<Object, E> _valueMap = (Map<Object, E>) clValueMap.get(enumType);
        if (_valueMap == null) {
            _valueMap = new LinkedHashMap<>();
            clValueMap.put(enumType, _valueMap);
        }
        @SuppressWarnings("unchecked")
        Map<V, E> valueMap = (Map<V, E>) _valueMap;
        return valueMap;
    }

    static <E extends EnumAlt<V, E>, V extends Serializable, Eb extends EnumAlt<V, Eb>> //
    void registerTree(Class<E> enumType, E entry) {
        Map<String, E> nameMap = getNameMap(enumType);
        Map<V, E> valueMap = getValueMap(enumType);
        nameMap.put(entry.getName(), entry);
        valueMap.put(entry.getValue(), entry);

        Class<?> _superclass = enumType.getSuperclass();
        if (_superclass != null && EnumAlt.class.isAssignableFrom(_superclass)) {
            @SuppressWarnings("unchecked")
            Class<Eb> baseEnumType = (Class<Eb>) _superclass;
            @SuppressWarnings("unchecked")
            Eb baseEntry = (Eb) entry;
            registerTree(baseEnumType, baseEntry);
        }
    }

    static {
        try {
            scanEnumTypes(true);
        } catch (ClassNotFoundException | IOException e) {
            throw new Error(e.getMessage(), e);
        }
    }

}
