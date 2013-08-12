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

    /**
     *
     *
     * <p lang="en">
     */
    public static final PrimefacesTheme aristo = new PrimefacesTheme("aristo");

    /**
     *
     *
     * <p lang="en">
     */
    public static final PrimefacesTheme cupertino = new PrimefacesTheme("cupertino");

    /**
     * 标准（清爽）
     *
     * <p lang="en">
     * Standard (Clean)
     */
    public static final PrimefacesTheme redmond = new PrimefacesTheme("redmond");

    /**
     * 标准（舒适）
     *
     * <p lang="en">
     * Standard (Humanity)
     */
    public static final PrimefacesTheme humanity = new PrimefacesTheme("humanity");

    /**
     * 蓝色天空
     *
     * <p lang="en">
     * Blue Sky
     */
    public static final PrimefacesTheme bluesky = new PrimefacesTheme("bluesky");

    /**
     * 商务黑
     *
     * <p lang="en">
     * Commercial
     */
    public static final PrimefacesTheme blackTie = new PrimefacesTheme("black-tie");

    /**
     * 苹果 X
     *
     * <p lang="en">
     * Glass X
     */
    public static final PrimefacesTheme glassX = new PrimefacesTheme("glass-x", 1);

    /**
     * 浪漫紫色
     *
     * <p lang="en">
     * Eggplant
     */
    public static final PrimefacesTheme eggplant = new PrimefacesTheme("eggplant", 1);

    /**
     * 皮都时尚
     *
     * <p lang="en">
     * Swanky Purse
     */
    public static final PrimefacesTheme swankyPurse = new PrimefacesTheme("swanky-purse", 1);

}
