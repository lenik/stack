package com.bee32.plover.web.faces.misc;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class ThemeSwitcherBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    GuestPreferences preferences;

    String theme;

    public Map<String, String> getThemes() {
        return themes;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void saveTheme() {
        preferences.setTheme(theme);
    }

    static final Map<String, String> themes;
    static {
        themes = new LinkedHashMap<String, String>();
        themes.put("标准（清爽）", "redmond");
        themes.put("标准（舒适）", "humanity");
        themes.put("商务黑", "black-tie");
        themes.put("蓝色天空", "bluesky");
        themes.put("浪漫紫色", "eggplant");
        themes.put("苹果 X", "glass-x");
        themes.put("皮都时尚", "swanky-purse");
    }

}
