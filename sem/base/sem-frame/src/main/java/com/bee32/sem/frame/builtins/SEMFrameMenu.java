package com.bee32.sem.frame.builtins;

import java.io.Serializable;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.bee32.plover.faces.misc.GuestPreferences;
import com.bee32.plover.faces.utils.FacesAssembledContext;
import com.bee32.plover.site.cfg.PrimefacesTheme;
import com.bee32.sem.frame.action.Action;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFrameMenu
        extends MenuComposite {

    static String VERSION = "SEM-Frame 0.3.2";

    public MenuNode MAIN = menu("main");

    public MenuNode START = menu(MAIN, 10, "start");
    /**/public MenuNode CONTROL = menu(START, 100, "control");
    /*    */public MenuNode SECURITY = menu(CONTROL, 100, "security");
    /*    */public MenuNode THEME = menu(CONTROL, 200, "theme");

    public MenuNode EDIT = menu(MAIN, 200, "edit");
    /**/public MenuNode ATTRIBUTES = menu(EDIT, 100, "attributes");

    public MenuNode DATA = menu(MAIN, 300, "data");

    public MenuNode RESOURCES = menu(MAIN, 310, "resources");
    public MenuNode BIZ1 = menu(MAIN, 320, "biz1");
    /**//*
          * static MenuNode contract = entry(BIZ1, 10, "contract",
          * JAVASCRIPT.join("alert('under development')"));
          */

    public MenuNode PROCESS = menu(MAIN, 600, "process");

    public MenuNode HELP = menu(MAIN, 10000, "help");

    @Override
    protected void preamble() {
        entry(HELP, 1, "aboutFrame", JAVASCRIPT.join("alert('" + VERSION + "')"));

        int index = 0;
        for (PrimefacesTheme theme : PrimefacesTheme.values()) {
            String label = theme.getEntryLabel();
            MenuNode node = entry(THEME, 100 + index++, label, null);
            Action action = new Action();
            action.setActionListener(new ThemeSwitcherActionListener(theme));
            node.setAction(action);
        }
    }

    public static MenuNode getMainMenu() {
        // ThreadLocalMenuAssembler
        return null;
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