package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMProcessMenu
        extends MenuContribution {

    static Location PROCESS_ = WEB_APP.join(SEMProcessModule.PREFIX + "/");

    public static MenuNode CORE = menu(SEMFrameMenu.PROCESS, 10, "process");
    public static MenuNode VERIFY_POLICY = menu(CORE, 10, "verifyPolicy");

    static MenuNode PREFERENCE = entry(CORE, 20, "preference", PROCESS_.join("pref/index-rich.jsf"));

    static MenuNode LIST = entry(VERIFY_POLICY, 1, "list", PROCESS_.join("v1/index-rich.jsf"));
    static MenuNode LEVEL = entry(VERIFY_POLICY, 2, "level", PROCESS_.join("v1x/index-rich.jsf"));

    // static MenuNode p2next = entry(verifyPolicy, 3, "p2next", PROCESS.join("p2next/index.do"));

    @Override
    protected void preamble() {
    }

}
