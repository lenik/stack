package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMWorkflowMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMProcessModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode WORKFLOW = menu(_frame_.OA, 30, "WORKFLOW");
    MenuNode my = entry(WORKFLOW, 10, "my", __.join("task/my"));
    MenuNode inbox = entry(WORKFLOW, 20, "inbox", __.join("task/inbox"));
    MenuNode queued = entry(WORKFLOW, 30, "queued", __.join("task/queued"));
    MenuNode archived = entry(WORKFLOW, 40, "archived", __.join("task/archived"));
    // MenuNode __1 = _separator_(WORKFLOW, 100);
    // MenuNode processModel= entry(WORKFLOW, 110, "processModel", __.join("process/model"));

}
