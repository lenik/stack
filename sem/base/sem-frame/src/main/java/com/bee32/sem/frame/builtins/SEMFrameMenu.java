package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFrameMenu
        extends MenuContribution {

    public static final MenuNode MAIN = menu("main");

    public static final MenuNode BASE = menu(MAIN, "base");
    public static final MenuNode RESOURCE = menu(MAIN, "resource");
    public static final MenuNode PROCESS = menu(MAIN, "process");
    public static final MenuNode SUPPORT = menu(MAIN, "support");
    public static final MenuNode BIZ1 = menu(MAIN, "biz1");
    public static final MenuNode HELP = menu(MAIN, "help");

    static String ABOUT_NAME = "SEM-Frame 0.3.2";
    static MenuNode ABOUT = entry(HELP, "aboutFrame", JAVASCRIPT.join("alert('" + ABOUT_NAME + "')"));

    @Override
    protected void preamble() {
    }

}
