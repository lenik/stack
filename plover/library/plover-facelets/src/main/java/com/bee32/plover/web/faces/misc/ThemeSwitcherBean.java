package com.bee32.plover.web.faces.misc;

import java.util.Map;

import javax.inject.Inject;

import com.bee32.plover.web.faces.view.PerView;
import com.bee32.plover.web.faces.view.ViewBean;

@PerView
public class ThemeSwitcherBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    GuestPreferences preferences;

    String theme;

    public Map<String, String> getThemes() {
        return ThemeData.getThemes();
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

    public void switchToRedmond() {
        preferences.setTheme("redmond");
    }

    public void switchToHumanity() {
        preferences.setTheme("humanity");
    }

    public void switchToBlackTie() {
        preferences.setTheme("black-tie");
    }

    public void switchToBluesky() {
        preferences.setTheme("bluesky");
    }

    public void switchToEggplant() {
        preferences.setTheme("eggplant");
    }

    public void switchToGlassX() {
        preferences.setTheme("glass-x");
    }

    public void switchToSwankyPurse() {
        preferences.setTheme("swanky-purse");
    }

}
