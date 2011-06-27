package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.SEMMainMenu;

public class SEMProcessMenu
        extends MenuContribution {

    static Location PROCESS = WEB_APP.join(SEMProcessModule.PREFIX + "/");

    public static MenuNode process = menu(SEMMainMenu.ADMIN, "process");
    public static MenuNode verifyPolicy = menu(process, "verifyPolicy");

    static MenuNode verifyPolicyPref = entry(process, "verifyPolicyPref", PROCESS.join("pref/index.do"));

    static MenuNode list = entry(verifyPolicy, 1, "list", PROCESS.join("list/index.do"));
    static MenuNode level = entry(verifyPolicy, 2, "level", PROCESS.join("level/index.do"));

    // static MenuNode p2next = entry(verifyPolicy, 3, "p2next", PROCESS.join("p2next/index.do"));

    @Override
    protected void preamble() {
    }

}
