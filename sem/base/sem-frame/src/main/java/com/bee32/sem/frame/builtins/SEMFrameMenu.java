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

    /**
     * 主菜单
     *
     * <p lang="en">
     * Main Menu
     */
    public MenuNode MAIN = menu("MAIN");

    /**
     * 系统
     *
     * <p lang="en">
     */
    public MenuNode SYSTEM = menu(MAIN, 10, "SYSTEM");

    /**
     * 界面风格
     *
     * <p lang="en">
     */
    /*    */public MenuNode THEME = menu(SYSTEM, 200, "THEME");

    /**
     * 属性
     *
     * <p lang="en">
     * Attributes
     */
    public MenuNode ATTRIBUTES = menu(SYSTEM, 100, "ATTRIBUTES");

    /**
     * 档案
     *
     * <p lang="en">
     * Data
     */
    public MenuNode DATA = menu(MAIN, 300, "DATA");

    /**
     * 资源
     *
     * <p lang="en">
     * Resources
     */
    public MenuNode RESOURCES = menu(MAIN, 310, "RESOURCES");

    /**
     * 基础资料
     *
     * <p lang="en">
     * General Business
     */
    public MenuNode BIZ1 = menu(MAIN, 320, "BIZ1");

    /**
     *
     *
     * <p lang="en">
     */
    public MenuNode BIZ2 = menu(MAIN, 321, "BIZ2");
    /**//*
          * static MenuNode contract = entry(BIZ1, 10, "contract",
          * JAVASCRIPT.join("alert('under development')"));
          */
    /**
     * 办公
     *
     * <p lang="en">
     */
    public MenuNode OA = menu(MAIN, 325, "OA");

    /**
     * 人事管理
     *
     * <p lang="en">
     */
    public MenuNode HR = menu(MAIN, 330, "HR");

    /**
     * 支持
     *
     * <p lang="en">
     */
    public MenuNode SUPPORT = menu(MAIN, 700, "SUPPORT");

    /**
     * 帮助
     *
     * <p lang="en">
     * Help
     */
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