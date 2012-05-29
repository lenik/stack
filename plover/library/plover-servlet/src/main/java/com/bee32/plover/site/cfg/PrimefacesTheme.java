package com.bee32.plover.site.cfg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

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

    static final Map<String, PrimefacesTheme> nameMap = new HashMap<String, PrimefacesTheme>();
    static final Map<String, PrimefacesTheme> valueMap = new HashMap<String, PrimefacesTheme>();

    public static Collection<PrimefacesTheme> values() {
        Collection<PrimefacesTheme> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static PrimefacesTheme forName(String altName) {
        PrimefacesTheme theme = nameMap.get(altName);
        if (theme == null)
            throw new NoSuchEnumException(PrimefacesTheme.class, altName);
        return theme;
    }

    public static PrimefacesTheme forValue(String value) {
        if (value == null)
            return null;

        PrimefacesTheme theme = valueMap.get(value);
        if (theme == null)
            throw new NoSuchEnumException(PrimefacesTheme.class, value);

        return theme;
    }

    public static final PrimefacesTheme redmond = new PrimefacesTheme("redmond");
    public static final PrimefacesTheme humanity = new PrimefacesTheme("humanity");
    public static final PrimefacesTheme bluesky = new PrimefacesTheme("bluesky");
    public static final PrimefacesTheme blackTie = new PrimefacesTheme("black-tie");
    public static final PrimefacesTheme glassX = new PrimefacesTheme("glass-x", 1);
    public static final PrimefacesTheme eggplant = new PrimefacesTheme("eggplant", 1);
    public static final PrimefacesTheme swankyPurse = new PrimefacesTheme("swanky-purse", 1);

}
