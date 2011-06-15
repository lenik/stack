package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMProcessMenu
        extends MenuContribution {

    static Location PROCESS = WEB_APP.join(SEMProcessModule.PREFIX);

    @Contribution("sa")
    MenuEntry process = new MenuEntry("process");

    @Contribution("sa/process")
    MenuEntry verifyPolicy = new MenuEntry("verifyPolicy");

    @Contribution("sa/process")
    MenuEntry verifyPolicyPref = new MenuEntry("verifyPolicyPref", PROCESS.join("pref/index.do"));

    @Contribution("sa/process/verifyPolicy")
    MenuEntry list = new MenuEntry(1, "list", PROCESS.join("list/index.do"));

    @Contribution("sa/process/verifyPolicy")
    MenuEntry level = new MenuEntry(2, "level", PROCESS.join("level/index.do"));

    // @Contribution("sa/process/verifyPolicy")
    // MenuEntry p2next = new MenuEntry(3, "p2next", PROCESS.join("p2next/index.do"));

    @Override
    protected void preamble() {
    }

}
