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

    public static final MenuNode FILE = menu(MAIN, 10, "file");
    public static final MenuNode EDIT = menu(MAIN, 20, "edit");
    public static final MenuNode DATA = menu(MAIN, 30, "data");
    public static final MenuNode RESOURCES = menu(MAIN, 40, "resources");
    public static final MenuNode BIZ1 = menu(MAIN, 50, "biz1");
    public static final MenuNode PROCESS = menu(MAIN, 60, "process");
    public static final MenuNode OPTIONS = menu(MAIN, 900, "options");
    public static final MenuNode HELP = menu(MAIN, 1000, "help");

    public static final MenuNode ATTRIBUTES = menu(EDIT, 100, "attributes");

    public static final MenuNode SYSTEM = menu(OPTIONS, 10, "system");
    public static final MenuNode SECURITY = menu(SYSTEM, 100, "security");
    public static final MenuNode PREFERENCES = menu(OPTIONS, 20, "preferences");
    public static final MenuNode THEME = menu(OPTIONS, 30, "theme");

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