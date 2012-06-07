package com.bee32.plover.site.cfg;

import java.util.Collection;

public class PrimefacesTheme
        extends SiteConfigEnum<String, PrimefacesTheme> {

    private static final long serialVersionUID = 1L;

    final int preference;

    private PrimefacesTheme(String name) {
        this(name, 0);
    }

    private PrimefacesTheme(String name, int preference) {
        super(name, name);
        this.preference = preference;
        if (preference == 0)
            PrimefacesThemes.addTheme(name, this);
    }

    public int getPreference() {
        return preference;
    }

    public static Collection<PrimefacesTheme> values() {
        return values(PrimefacesTheme.class);
    }

    public static PrimefacesTheme forName(String altName) {
        return forName(PrimefacesTheme.class, altName);
    }

    public static PrimefacesTheme forValue(String value) {
        return forValue(PrimefacesTheme.class, value);
    }

    public static final PrimefacesTheme redmond = new PrimefacesTheme("redmond");
    public static final PrimefacesTheme humanity = new PrimefacesTheme("humanity");
    public static final PrimefacesTheme bluesky = new PrimefacesTheme("bluesky");
    public static final PrimefacesTheme blackTie = new PrimefacesTheme("black-tie");
    public static final PrimefacesTheme glassX = new PrimefacesTheme("glass-x", 1);
    public static final PrimefacesTheme eggplant = new PrimefacesTheme("eggplant", 1);
    public static final PrimefacesTheme swankyPurse = new PrimefacesTheme("swanky-purse", 1);

}
