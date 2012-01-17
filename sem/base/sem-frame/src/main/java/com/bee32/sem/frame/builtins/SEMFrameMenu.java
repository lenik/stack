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

    static String VERSION = "SEM-Frame 0.3.2";

    public static final MenuNode MAIN = menu("main");

    public static final MenuNode BASE = menu(MAIN, 10, "base");
    public static final MenuNode DATA = menu(MAIN, 20, "data");
    public static final MenuNode RESOURCE = menu(MAIN, 30, "resource");
    public static final MenuNode PROCESS = menu(MAIN, 40, "process");
    public static final MenuNode SUPPORT = menu(MAIN, 50, "support");
    public static final MenuNode BIZ1 = menu(MAIN, 60, "biz1");
    public static final MenuNode EDIT = menu(MAIN, 70, "edit");
    public static final MenuNode VIEW = menu(MAIN, 80, "view");
    public static final MenuNode HELP = menu(MAIN, 90, "help");

    public static final MenuNode ATTRIBUTES = menu(EDIT, "attributes");
    public static final MenuNode THEME = menu(VIEW, "theme");

    static MenuNode ABOUT = entry(HELP, "aboutFrame", JAVASCRIPT.join("alert('" + VERSION + "')"));

    @Override
    protected void preamble() {
        for (PrimefacesTheme theme : PrimefacesTheme.values()) {
            String label = theme.getEntryLabel();
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