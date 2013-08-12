package com.bee32.plover.faces.misc;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.PrimefacesTheme;

@Component
@Scope("session")
public class GuestPreferences
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static PrimefacesTheme defaultTheme = PrimefacesTheme.aristo;

    PrimefacesTheme viewTheme;

    public GuestPreferences() {
    }

    public PrimefacesTheme getTheme() {
        if (viewTheme != null)
            return viewTheme;

        SiteInstance site = ThreadHttpContext.getSiteInstance();
        PrimefacesTheme theme = site.getTheme();
        if (theme != null)
            return theme;

        return defaultTheme;
    }

    public void setTheme(PrimefacesTheme theme) {
        viewTheme = theme;
    }

}
