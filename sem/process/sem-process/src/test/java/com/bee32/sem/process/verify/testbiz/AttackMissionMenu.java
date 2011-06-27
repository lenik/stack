package com.bee32.sem.process.verify.testbiz;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class AttackMissionMenu
        extends MenuContribution {

    static Location ATTACK = WEB_APP.join(AttackMissionController.PREFIX);

    static MenuNode attack = entry(SEMFrameMenu.MAIN, "attack", ATTACK.join("index.do"));

    @Override
    protected void preamble() {
    }

}
