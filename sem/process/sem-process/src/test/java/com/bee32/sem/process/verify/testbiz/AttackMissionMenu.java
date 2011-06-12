package com.bee32.sem.process.verify.testbiz;

import com.bee32.plover.servlet.context.Location;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class AttackMissionMenu
        extends MenuContribution {

    static Location ATTACK = WEB_APP.join(AttackMissionController.PREFIX);

    @Contribution("help")
    MenuEntry attack = new MenuEntry("attack", ATTACK.join("index.do"));

    @Override
    protected void preamble() {
    }

}
