package com.bee32.plover.ox1.principal;

import com.bee32.icsf.principal.IcsfPrincipalModule;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfPrincipalMenu
        extends MenuComposite {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(IcsfPrincipalModule.PREFIX_);

    MenuNode userAdmin = entry(_frame_.SYSTEM, 10, "userAdmin", prefix.join("user/index-rich.jsf"));
    MenuNode groupAdmin = entry(_frame_.SYSTEM, 11, "groupAdmin", prefix.join("group/index-rich.jsf"));
    // MenuNode roleAdmin = entry(IDENTITY, 12, "roleAdmin", prefix.join("role/index-rich.jsf"));

    MenuNode modifyPassword = entry(_frame_.SYSTEM, 50, "modifyPassword", prefix.join("modifyPassword.jsf"));

}
