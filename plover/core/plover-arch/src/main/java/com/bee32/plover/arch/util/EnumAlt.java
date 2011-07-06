package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.bee32.plover.arch.util.res.ResourceBundleUTF8;
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
 *     public static ${EnumType} valueOf(String altName) {
 *         ${EnumType} ${typeName:localVar} = nameMap.get(altName);
 *         if (${typeName:localVar} == null)
 *             throw new NoSuchEnumException(${EnumType}.class, altName);
 *         return ${typeName:localVar};
 *     }
 *
 * }
 * </pre>
 */
public abstract class EnumAlt<V extends Serializable, $ extends EnumAlt<V, $>>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final V value;
    protected final String name;
    protected final String label;
    protected final Location icon;

    public EnumAlt(V value, String name) {
        if (name == null)
            throw new NullPointerException("name");
        if (value == null)
            throw new NullPointerException("value");

        this.name = name;
        this.value = value;

        this.label = _nls(name + ".label", name);

        String icon = _nls(name + ".icon", null);
        if (icon != null)
            this.icon = Locations.parse(icon);
        else
            this.icon = null;

        @SuppressWarnings("unchecked")
        $ _this = ($) this;

        getNameMap().put(name, _this);
        getValueMap().put(value, _this);
    }

    /**
     * Get the name map of this enumeration type.
     *
     * @return A non-<code>null</code> map contains all the enum elements.
     */
    protected abstract Map<String, $> getNameMap();

    /**
     * Get the value map of this enumeration type.
     *
     * @return A non-<code>null</code> map contains all the enum elements.
     */
    protected abstract Map<V, $> getValueMap();

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
        if (label != null)
            return label;
        else
            return name;
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

}
