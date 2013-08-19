package com.bee32.plover.ox1.principal;

import com.bee32.icsf.principal.IcsfPrincipalModule;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * SEM 安全主体菜单
 *
 * <p lang="en">
 * ICSF Principal Menu
 */
public class IcsfPrincipalMenu
        extends MenuComposite {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(IcsfPrincipalModule.PREFIX_);

    /**
     * 用户管理
     *
     * <p lang="en">
     * User Admin
     */
    MenuNode userAdmin = entry(_frame_.SYSTEM, 10, "userAdmin", prefix.join("user/index-rich.jsf"));

    /**
     * 组管理
     *
     * <p lang="en">
     * Group Admin
     */
    MenuNode groupAdmin = entry(_frame_.SYSTEM, 11, "groupAdmin", prefix.join("group/index-rich.jsf"));

    /**
     * 角色管理
     *
     * <p lang="en">
     * Role Admin
     */
    // MenuNode roleAdmin = entry(IDENTITY, 12, "roleAdmin", prefix.join("role/index-rich.jsf"));

    /**
     * 修改密码
     *
     * <p lang="en">
     * Modify Password...
     */
    MenuNode modifyPassword = entry(_frame_.SYSTEM, 50, "modifyPassword", prefix.join("modifyPassword.jsf"));

}
