package com.bee32.plover.faces.misc;

import java.util.Map;

import javax.inject.Inject;

import com.bee32.plover.faces.view.PerView;
import com.bee32.plover.faces.view.ViewBean;
import com.bee32.plover.site.cfg.PrimefacesTheme;
import com.bee32.plover.site.cfg.PrimefacesThemes;

@PerView
public class ThemeSwitcherBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    GuestPreferences preferences;

    String themeName;

    public Map<String, PrimefacesTheme> getThemes() {
        return PrimefacesThemes.getMap();
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public void saveTheme() {
        PrimefacesTheme theme = PrimefacesTheme.valueOf(themeName);
        preferences.setTheme(theme);
    }

    public void switchToRedmond() {
        preferences.setTheme(PrimefacesTheme.redmond);
    }

    public void switchToHumanity() {
        preferences.setTheme(PrimefacesTheme.humanity);
    }

    public void switchToBlackTie() {
        preferences.setTheme(PrimefacesTheme.blackTie);
    }

    public void switchToBluesky() {
        preferences.setTheme(PrimefacesTheme.bluesky);
    }

    public void switchToEggplant() {
        preferences.setTheme(PrimefacesTheme.eggplant);
    }

    public void switchToGlassX() {
        preferences.setTheme(PrimefacesTheme.glassX);
    }

    public void switchToSwankyPurse() {
        preferences.setTheme(PrimefacesTheme.swankyPurse);
    }

}
