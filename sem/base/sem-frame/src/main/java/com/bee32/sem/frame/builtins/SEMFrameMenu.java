package com.bee32.sem.frame.builtins;

import java.io.Serializable;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.bee32.plover.faces.misc.GuestPreferences;
import com.bee32.plover.faces.utils.FacesAssembledContext;
import com.bee32.plover.site.cfg.PrimefacesTheme;
import com.bee32.sem.frame.action.Action;
import com.bee32.sem.frame.menu.ContextMenuAssembler;
import com.bee32.sem.frame.menu.IMenuAssembler;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFrameMenu
        extends MenuComposite {

    static String VERSION = "SEM-Frame 0.3.2";

    public MenuNode MAIN = menu("MAIN");

    public MenuNode START = menu(MAIN, 10, "START");
    /**/public MenuNode CONTROL = menu(START, 100, "CONTROL");
    /*    */public MenuNode SECURITY = menu(CONTROL, 100, "SECURITY");
    /*    */public MenuNode THEME = menu(CONTROL, 200, "THEME");

    public MenuNode EDIT = menu(MAIN, 200, "EDIT");
    /**/public MenuNode ATTRIBUTES = menu(EDIT, 100, "ATTRIBUTES");

    public MenuNode DATA = menu(MAIN, 300, "DATA");

    public MenuNode RESOURCES = menu(MAIN, 310, "RESOURCES");
    public MenuNode BIZ1 = menu(MAIN, 320, "BIZ1");
    public MenuNode BIZ2 = menu(MAIN, 321, "BIZ2");
    /**//*
          * static MenuNode contract = entry(BIZ1, 10, "contract",
          * JAVASCRIPT.join("alert('under development')"));
          */

    public MenuNode HR = menu(MAIN, 330, "HR");

    public MenuNode PROCESS = menu(MAIN, 600, "PROCESS");
    public MenuNode SUPPORT = menu(MAIN, 700, "SUPPORT");

    public MenuNode HELP = menu(MAIN, 10000, "HELP");

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

    @Deprecated
    public static MenuNode getMainMenu() {
        IMenuAssembler contextAssembler = ContextMenuAssembler.getMenuAssembler();
        if (contextAssembler == null)
            throw new IllegalStateException("No context menu assembler set.");
        return contextAssembler.require(SEMFrameMenu.class).MAIN;
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