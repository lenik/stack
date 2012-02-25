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

    static MenuNode acl = entry(SEMFrameMenu.SECURITY, 31, "acl", BASE_.join("acl/"));
    static MenuNode aclPref = entry(SEMFrameMenu.SECURITY, 32, "aclPref", BASE_.join("acl-pref/"));
    static MenuNode r_ace = entry(SEMFrameMenu.SECURITY, 33, "r_ace", BASE_.join("r-ace/"));

}
