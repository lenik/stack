package com.bee32.sem.event;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.event.web.EventController;
import com.bee32.sem.event.web.EventPriorityController;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMEventMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    static Location __ = WEB_APP.join(EventController.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 事件
     *
     * <p lang="en">
     * Events
     */
    public MenuNode EVENT = menu(_frame_.SYSTEM, 21, "EVENT");

    /**
     * 设置
     *
     * <p lang="en">
     * Admin
     */
    public MenuNode ADMIN = menu(EVENT, 100, "ADMIN"); // MAIN/Admin

    /**
     * 事件分类
     *
     * <p lang="en">
     * Event Categories
     */
    MenuNode categories = entry(ADMIN, 10, "categories", getDictIndex(EventCategory.class));

    /**
     * 事件优先级
     *
     * <p lang="en">
     * Event Priorities
     */
    MenuNode priorities = entry(ADMIN, 11, "priorities", //
            WEB_APP.join(EventPriorityController.PREFIX_).join("index.do"));

    /**
     * 事件状态
     *
     * <p lang="en">
     * Event States
     */
    MenuNode states = entry(ADMIN, 12, "states", getDictIndex(EventStatus.class));

    /**
     * 待办事项
     *
     * <p lang="en">
     * Pending Tasks
     */
    MenuNode pendingTasks = entry(EVENT, 10, "pendingTasks", __.join("index.do?type=t&closed=false"));

    /**
     * 已办事项
     *
     * <p lang="en">
     * Completed Tasks
     */
    MenuNode completedTasks = entry(EVENT, 11, "completedTasks", __.join("index.do?type=t&closed=true"));

    /**
     * 最近事件
     *
     * <p lang="en">
     * Recent Events
     */
    MenuNode eventIndex = entry(EVENT, 20, "eventIndex", __.join("index.do?recent=30"));

}
