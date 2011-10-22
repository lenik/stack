package com.bee32.plover.web.faces.misc;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class GuestPreferences
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String DEFAULT_THEME = "bluesky";

    String theme = DEFAULT_THEME;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}