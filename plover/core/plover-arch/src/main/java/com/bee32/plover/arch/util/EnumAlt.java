package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import com.bee32.plover.arch.util.res.INlsBundle;
import com.bee32.plover.arch.util.res.NlsBundles;
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
public abstract class EnumAlt<V extends Serializable, self_t extends EnumAlt<V, self_t>>
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
    protected final Map<String, self_t> getNameMap() {
        @SuppressWarnings("unchecked")
        Map<String, self_t> nameMap = (Map<String, self_t>) getNameMap(getClass());
        return nameMap;
    }

    /**
     * Get the value map of this enumeration type.
     *
     * @return A non-<code>null</code> map contains all the enum elements.
     */
    protected final Map<V, self_t> getValueMap() {
        @SuppressWarnings("unchecked")
        Map<V, self_t> valueMap = (Map<V, self_t>) getValueMap(getClass());
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

        INlsBundle rb;
        try {
            rb = NlsBundles.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            return def;
        }

        if (rb.containsKey(key))
            return rb.getString(key);
        else
            return def;
    }

    private static <E extends EnumAlt<?, E>> Map<String, E> getNameMap(Class<E> enumType) {
        return EnumAltRegistry.nameMap(enumType);
    }

    private static <E extends EnumAlt<V, E>, V extends Serializable> Map<V, E> getValueMap(Class<E> enumType) {
        return EnumAltRegistry.valueMap(enumType);
    }

    protected static <E extends EnumAlt<V, E>, V extends Serializable> Collection<E> values(Class<E> enumType) {
        Collection<E> values = getValueMap(enumType).values();
        return Collections.unmodifiableCollection(values);
    }

    protected static <E extends EnumAlt<V, E>, V extends Serializable> E forName(Class<E> enumType, String name) {
        E entry = getNameMap(enumType).get(name);
        if (entry == null)
            throw new NoSuchEnumException(enumType, name);
        return entry;
    }

    protected static <E extends EnumAlt<V, E>, V extends Serializable> E forValue(Class<E> enumType, V value) {
        if (value == null)
            throw new NullPointerException("value");
        E entry = getValueMap(enumType).get(value);
        if (entry == null)
            throw new NoSuchEnumException(enumType, "value=" + value);
        return entry;
    }

}
