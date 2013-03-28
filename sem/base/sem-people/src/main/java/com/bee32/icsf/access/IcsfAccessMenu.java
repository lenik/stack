package com.bee32.icsf.access;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfAccessMenu
        extends MenuComposite {

    static Location BASE_ = WEB_APP.join(IcsfAccessModule.PREFIX_);

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);



    public MenuNode SECURITY = menu(_frame_.SYSTEM, 200, "SECURITY");

    MenuNode acl = entry(SECURITY, 31, "acl", BASE_.join("acl/"));
    MenuNode aclPref = entry(SECURITY, 32, "aclPref", BASE_.join("acl-pref/"));
    MenuNode recordSecurity = entry(SECURITY, 33, "recordSecurity", //
            JAVASCRIPT.join("loadAclAndShow(securityDialog)"));


    MenuNode r_ace = entry(_frame_.SYSTEM, 33, "r_ace", BASE_.join("r-ace/"));

    @Override
    protected void preamble() {
    }

}
