package com.bee32.plover.web.faces.misc;

import java.util.LinkedHashMap;
import java.util.Map;

public class ThemeData {

    public static Map<String, String> getThemes() {
        return themes;
    }

    static boolean more = false;

    static final Map<String, String> themes;
    static {
        themes = new LinkedHashMap<String, String>();
        themes.put("标准（舒适）", "humanity");
        if (more) {
            themes.put("标准（清爽）", "redmond");
            themes.put("蓝色天空", "bluesky");
            themes.put("商务黑", "black-tie");
            themes.put("浪漫紫色", "eggplant");
            themes.put("苹果 X", "glass-x");
            themes.put("皮都时尚", "swanky-purse");
        }
    }

}
