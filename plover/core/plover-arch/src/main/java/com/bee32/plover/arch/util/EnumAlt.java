package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.free.SystemCLG;

import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;

public abstract class EnumAlt<V extends Serializable, $ extends EnumAlt<V, $>>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final V value;
    protected final String name;
    protected final String displayName;
    protected final Location icon;

    public EnumAlt(V value, String name) {
        if (name == null)
            throw new NullPointerException("name");
        if (value == null)
            throw new NullPointerException("value");

        this.name = name;
        this.value = value;

        this.displayName = _nls(name + ".label", name);

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

    public String getDisplayName() {
        return displayName;
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
        return displayName;
    }

    protected String _nls(String key, String def) {
        Locale locale = SystemCLG.locale.get();

        ResourceBundle rb;

        try {
            rb = ResourceBundle.getBundle(getClass().getName(), locale);
        } catch (MissingResourceException e) {
            return def;
        }

        if (rb.containsKey(key))
            return rb.getString(key);
        else
            return def;
    }

}
