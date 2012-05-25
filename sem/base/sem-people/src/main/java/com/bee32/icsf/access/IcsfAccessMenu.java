package com.bee32.icsf.access;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfAccessMenu
        extends MenuComposite {

    static Location BASE_ = WEB_APP.join(IcsfAccessModule.PREFIX_);

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    MenuNode security = entry(_frame_.ATTRIBUTES, 100, "security", //
            JAVASCRIPT.join("loadAclAndShow(securityDialog)"));
    MenuNode acl = entry(_frame_.SECURITY, 31, "acl", BASE_.join("acl/"));
    MenuNode aclPref = entry(_frame_.SECURITY, 32, "aclPref", BASE_.join("acl-pref/"));
    MenuNode r_ace = entry(_frame_.SECURITY, 33, "r_ace", BASE_.join("r-ace/"));

    @Override
    protected void preamble() {
    }

}
