package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * 企业过程菜单
 *
 * <p lang="en">
 * SEM Process Menu
 */
public class SEMProcessMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMProcessModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 审核策略配置
     *
     * <p lang="en">
     * Verify Policies
     */
    public MenuNode VERIFY_POLICY = menu(_frame_.SYSTEM, 700, "VERIFY_POLICY");

    /**
     * 审核策略应用
     *
     * <p lang="en">
     * Verify Policy Apply
     */
    MenuNode preference = entry(_frame_.SYSTEM, 800, "preference", __.join("pref"));

    /**
     * 白名单策略
     *
     * <p lang="en">
     * White List
     */
    MenuNode list = entry(VERIFY_POLICY, 1, "list", __.join("v1"));

    /**
     * 分级策略
     *
     * <p lang="en">
     * Level Map
     */
    MenuNode level = entry(VERIFY_POLICY, 2, "level", __.join("v1x"));

    /**
     *
     *
     * <p lang="en">
     */
    MenuNode processStateOprator = entry(VERIFY_POLICY, 3, "processStateOprator", __.join("state"));

    /**
     * 下一步策略
     *
     * <p lang="en">
     * Pass to next
     */
    // MenuNode p2next = entry(verifyPolicy, 3, "p2next", __.join("p2next/index.do"));

}
