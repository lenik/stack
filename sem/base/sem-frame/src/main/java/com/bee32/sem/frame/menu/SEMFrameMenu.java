package com.bee32.sem.frame.menu;

public class SEMFrameMenu
        extends MenuContribution {

    public static final MenuNode MAIN = menu("main");
    public static final MenuNode ADMIN = menu(MAIN, "admin");
    public static final MenuNode HELP = menu(MAIN, "help");

    @Override
    protected void preamble() {
    }

}
