package com.bee32.sem.process;

import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMProcessMenu
        extends MenuContribution {

    @Contribution("sa")
    MenuEntry process = new MenuEntry("process");

    @Contribution("sa/process")
    MenuEntry verifyPolicy = new MenuEntry("verifyPolicy");

    @Contribution("sa/process")
    MenuEntry verifyPolicyPref = new MenuEntry("verifyPolicyPref");

    @Contribution("sa/process/verifyPolicy")
    MenuEntry list = new MenuEntry(1, "list", WEB_APP.join("3/15/2/1/list/index.htm"));

    @Contribution("sa/process/verifyPolicy")
    MenuEntry level = new MenuEntry(2, "level", WEB_APP.join("3/15/2/1/level/index.htm"));

    // @Contribution("sa/process/verifyPolicy")
    // MenuEntry p2next = new MenuEntry(3, "p2next", WEB_APP.join("3/15/2/1/p2next/index.htm"));

    @Override
    protected void preamble() {
    }

}
