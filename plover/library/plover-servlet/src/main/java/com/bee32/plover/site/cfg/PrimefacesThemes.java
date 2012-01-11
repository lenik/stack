package com.bee32.plover.site.cfg;

import java.util.LinkedHashMap;
import java.util.Map;

public class PrimefacesThemes {

    private static Map<String, PrimefacesTheme> map = new LinkedHashMap<String, PrimefacesTheme>();

    public static Map<String, PrimefacesTheme> getMap() {
        return map;
    }

    public static void addTheme(String key, PrimefacesTheme theme) {
        map.put(key, theme);
    }

}
