package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * APEX 工作流菜单
 *
 * <p lang="en">
 * SEM Workflow Menu
 */
public class SEMWorkflowMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMProcessModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 系统作业
     *
     * <p lang="en">
     * Tasks
     */
    public MenuNode WORKFLOW = menu(_frame_.OA, 30, "WORKFLOW");

    /**
     * 我的作业
     *
     * <p lang="en">
     * My Tasks
     */
    MenuNode my = entry(WORKFLOW, 10, "my", __.join("task/my"));

    /**
     * 待办作业
     *
     * <p lang="en">
     * Task Inbox
     */
    MenuNode inbox = entry(WORKFLOW, 20, "inbox", __.join("task/inbox"));

    /**
     * 作业队列
     *
     * <p lang="en">
     * Queued Tasks
     */
    MenuNode queued = entry(WORKFLOW, 30, "queued", __.join("task/queued"));

    /**
     * 已办作业
     *
     * <p lang="en">
     * Archived Tasks
     */
    MenuNode archived = entry(WORKFLOW, 40, "archived", __.join("task/archived"));

    /**
     *
     *
     * <p lang="en">
     */
    // MenuNode __1 = _separator_(WORKFLOW, 100);

    /**
     *
     *
     * <p lang="en">
     */
    // MenuNode processModel= entry(WORKFLOW, 110, "processModel", __.join("process/model"));

}
