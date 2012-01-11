package com.bee32.sem.frame.builtins;

import java.io.Serializable;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.bee32.plover.site.cfg.PrimefacesTheme;
import com.bee32.plover.web.faces.misc.GuestPreferences;
import com.bee32.plover.web.faces.utils.FacesContextSupport;
import com.bee32.sem.frame.action.Action;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFrameMenu
        extends MenuContribution {

    public static final MenuNode MAIN = menu("main");

    public static final MenuNode BASE = menu(MAIN, "base");
    public static final MenuNode DATA = menu(MAIN, "data");
    public static final MenuNode RESOURCE = menu(MAIN, "resource");
    public static final MenuNode PROCESS = menu(MAIN, "process");
    public static final MenuNode SUPPORT = menu(MAIN, "support");
    public static final MenuNode BIZ1 = menu(MAIN, "biz1");
    public static final MenuNode VIEW = menu(MAIN, "view");
    public static final MenuNode HELP = menu(MAIN, "help");

    public static final MenuNode THEME = menu(VIEW, "theme");

    static String ABOUT_NAME = "SEM-Frame 0.3.2";
    static MenuNode ABOUT = entry(HELP, "aboutFrame", JAVASCRIPT.join("alert('" + ABOUT_NAME + "')"));

    @Override
    protected void preamble() {
        for (PrimefacesTheme theme : PrimefacesTheme.values()) {
            String label = theme.getLabel();
            MenuNode node = entry(THEME, label, null);
            Action action = new Action();
            action.setActionListener(new ThemeSwitcherActionListener(theme));
            node.setAction(action);
        }
    }

}

class ThemeSwitcherActionListener
        implements ActionListener, Serializable {

    private static final long serialVersionUID = 1L;

    PrimefacesTheme theme;

    public ThemeSwitcherActionListener() {
    }

    public ThemeSwitcherActionListener(PrimefacesTheme theme) {
        if (theme == null)
            throw new NullPointerException("theme");
        this.theme = theme;
    }

    @Override
    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        GuestPreferences pref = FacesContextSupport.getBean(GuestPreferences.class);
        pref.setTheme(theme);
    }

}