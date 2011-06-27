package com.bee32.sem.process.verify.testbiz;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.SEMMainMenu;

public class AttackMissionMenu
        extends MenuContribution {

    static Location ATTACK = WEB_APP.join(AttackMissionController.PREFIX);

    static MenuNode attack = entry(SEMMainMenu.MAIN, "attack", ATTACK.join("index.do"));

    @Override
    protected void preamble() {
    }

}
