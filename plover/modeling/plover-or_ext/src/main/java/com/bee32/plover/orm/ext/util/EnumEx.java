package com.bee32.plover.orm.ext.util;

import java.io.Serializable;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.bee32.plover.servlet.context.Location;
import com.bee32.plover.servlet.context.Locations;

public abstract class EnumEx<$ extends EnumEx<$>>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final int id;
    protected final String name;
    protected final String displayName;
    protected final Location icon;

    public EnumEx(int id, String name) {
        this.id = id;
        this.name = name;

        this.displayName = _nls(name + ".label", name);

        String icon = _nls(name + ".icon", null);
        if (icon != null)
            this.icon = Locations.parse(icon);
        else
            this.icon = null;

        @SuppressWarnings("unchecked")
        $ _this = ($) this;

        getMap().put(id, _this);
    }

    protected abstract Map<Integer, $> getMap();

    protected String _nls(String key, String def) {
        ResourceBundle rb;

        try {
            rb = ResourceBundle.getBundle(getClass().getName());
        } catch (MissingResourceException e) {
            return def;
        }

        if (rb.containsKey(key))
            return rb.getString(key);
        else
            return def;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Location getIcon() {
        return icon;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EnumEx<?>))
            return false;
        EnumEx<?> o = (EnumEx<?>) obj;
        return id == o.id;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
