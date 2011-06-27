package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMProcessMenu
        extends MenuContribution {

    static Location PROCESS_ = WEB_APP.join(SEMProcessModule.PREFIX + "/");

    public static MenuNode CORE = menu(SEMFrameMenu.PROCESS, "process");
    public static MenuNode VERIFY_POLICY = menu(CORE, "verifyPolicy");

    static MenuNode PREFERENCE = entry(CORE, "preference", PROCESS_.join("pref/index.do"));

    static MenuNode LIST = entry(VERIFY_POLICY, 1, "list", PROCESS_.join("list/index.do"));
    static MenuNode LEVEL = entry(VERIFY_POLICY, 2, "level", PROCESS_.join("level/index.do"));

    // static MenuNode p2next = entry(verifyPolicy, 3, "p2next", PROCESS.join("p2next/index.do"));

    @Override
    protected void preamble() {
    }

}
