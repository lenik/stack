package com.bee32.icsf.access;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfAccessMenu
        extends MenuContribution {

    static Location BASE_ = WEB_APP.join(IcsfAccessModule.PREFIX_);

    static MenuNode security = entry(SEMFrameMenu.ATTRIBUTES, 100, "security", //
            JAVASCRIPT.join("loadAclAndShow(securityDialog)"));

    static MenuNode acl = entry(SEMFrameMenu.SECURITY, 10, "acl", BASE_.join("acl/index-rich.jsf"));
    static MenuNode permissionAdmin = entry(SEMFrameMenu.SECURITY, 10, "permissionAdmin",
            BASE_.join("r-ace/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
