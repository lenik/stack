package com.bee32.sem.frame.builtins;

import java.io.Serializable;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.bee32.plover.faces.misc.GuestPreferences;
import com.bee32.plover.faces.utils.FacesAssembledContext;
import com.bee32.plover.site.cfg.PrimefacesTheme;
import com.bee32.sem.frame.action.Action;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFrameMenu
        extends MenuContribution {

    static String VERSION = "SEM-Frame 0.3.2";

    public static final MenuNode MAIN = menu("main");
    /**/public static final MenuNode FILE = menu(MAIN, 100, "file");
    /*    */static MenuNode print = entry(FILE, 100, "print", JAVASCRIPT.join("alert('under development')"));
    /**/public static final MenuNode EDIT = menu(MAIN, 200, "edit");
    /*    */public static final MenuNode ATTRIBUTES = menu(EDIT, 100, "attributes");
    /**/public static final MenuNode DATA = menu(MAIN, 300, "data");
    /*    */static MenuNode contract = entry(DATA, 50, "contract", JAVASCRIPT.join("alert('under development')"));
    /**/public static final MenuNode RESOURCES = menu(MAIN, 400, "resources");
    /**/public static final MenuNode BIZ1 = menu(MAIN, 500, "biz1");
    /**/public static final MenuNode PROCESS = menu(MAIN, 600, "process");
    /**/public static final MenuNode OPTIONS = menu(MAIN, 9000, "options");
    /*    */public static final MenuNode SYSTEM = menu(OPTIONS, 10, "system");
    /*        */public static final MenuNode SECURITY = menu(SYSTEM, 100, "security");
    /*    */public static final MenuNode PREFERENCES = menu(OPTIONS, 20, "preferences");
    /*    */public static final MenuNode THEME = menu(OPTIONS, 30, "theme");
    /**/public static final MenuNode HELP = menu(MAIN, 10000, "help");
    /*    */static MenuNode about = entry(HELP, 1, "aboutFrame", JAVASCRIPT.join("alert('" + VERSION + "')"));


    @Override
    protected void preamble() {
        int index = 0;
        for (PrimefacesTheme theme : PrimefacesTheme.values()) {
            String label = theme.getEntryLabel();
            MenuNode node = entry(THEME, 100 + index++, label, null);
            Action action = new Action();
            action.setActionListener(new ThemeSwitcherActionListener(theme));
            node.setAction(action);
        }
    }

}

class ThemeSwitcherActionListener
        implements ActionListener, Serializable {

    private static final long serialVersionUID = 1L;

    static class ctx
            extends FacesAssembledContext {
    }

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
        GuestPreferences pref = ctx.bean.getBean(GuestPreferences.class);
        pref.setTheme(theme);
    }

}