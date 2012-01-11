package com.bee32.plover.web.faces.misc;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;

@Component
@Scope("session")
public class GuestPreferences
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String DEFAULT_THEME = "redmond";

    String theme;

    public GuestPreferences() {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        String theme = site.getProperty("theme");
        if (theme == null)
            theme = DEFAULT_THEME;
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
