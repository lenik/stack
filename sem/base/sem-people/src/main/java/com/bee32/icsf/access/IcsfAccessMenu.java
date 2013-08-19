package com.bee32.icsf.access;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 *
 *
 * <p lang="en">
 */
public class IcsfAccessMenu
        extends MenuComposite {

    static Location BASE_ = WEB_APP.join(IcsfAccessModule.PREFIX_);

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 安全策略
     *
     * <p lang="en">
     */
    public MenuNode SECURITY = menu(_frame_.SYSTEM, 200, "SECURITY");

    /**
     * 安全策略定义
     *
     * <p lang="en">
     * ACL Definition
     */
    MenuNode acl = entry(SECURITY, 31, "acl", BASE_.join("acl/"));

    /**
     * 默认记录安全
     *
     * <p lang="en">
     * Entity/ACL Preferences
     */
    MenuNode aclPref = entry(SECURITY, 32, "aclPref", BASE_.join("acl-pref/"));

    /**
     * 记录安全…
     *
     * <p lang="en">
     * Record Security...
     */
    MenuNode recordSecurity = entry(SECURITY, 33, "recordSecurity", //
            JAVASCRIPT.join("loadAclAndShow(securityDialog)"));

    /**
     * 权限管理
     *
     * <p lang="en">
     * Resource Permissions
     */
    MenuNode r_ace = entry(_frame_.SYSTEM, 33, "r_ace", BASE_.join("r-ace/"));

    @Override
    protected void preamble() {
    }

}
