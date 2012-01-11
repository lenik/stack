package com.bee32.plover.site.cfg;

import java.util.Locale;
import java.util.ResourceBundle;

import com.bee32.plover.arch.util.res.ResourceBundleUTF8;

public enum PrimefacesTheme
        implements ILabel {

    redmond("redmond"),

    humanity("humanity"),

    bluesky("bluesky"),

    blackTie("black-tie"),

    glassX("glass-x", 1),

    eggplant("eggplant", 1),

    swankyPurse("swanky-purse", 1),

    ;

    final String id;
    final int preference;
    String label;
    String description;

    private PrimefacesTheme(String id) {
        this(id, 0);
    }

    private PrimefacesTheme(String id, int preference) {
        this.id = id;
        this.preference = preference;
        PrimefacesThemes.addTheme(name(), this);
    }

    public String getId() {
        return id;
    }

    public int getPreference() {
        return preference;
    }

    static ResourceBundle rb;
    static {
        String baseName = PrimefacesTheme.class.getName().replace('.', '/');
        rb = ResourceBundleUTF8.getBundle(baseName, Locale.getDefault());
    }

    @Override
    public String getLabel() {
        return rb.getString(name() + ".label");
    }

    public String getDisplayName() {
        String label = getLabel();
        if (label == null || label.isEmpty())
            return name();
        else
            return label;
    }

    public String getDescription() {
        return rb.getString(name() + ".description");
    }

}
