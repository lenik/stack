package com.bee32.plover.ox1.principal;

import com.bee32.icsf.principal.IcsfPrincipalModule;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfPrincipalMenu
        extends MenuComposite {

    static Location PRINCIPAL_ = WEB_APP.join(IcsfPrincipalModule.PREFIX_);

    public static transient MenuNode IDENTITY = SEMFrameMenu.SECURITY;

    static MenuNode userAdmin = entry(IDENTITY, 10, "userAdmin", PRINCIPAL_.join("user/index-rich.jsf"));
    static MenuNode groupAdmin = entry(IDENTITY, 11, "groupAdmin", PRINCIPAL_.join("group/index-rich.jsf"));
    static MenuNode roleAdmin = entry(IDENTITY, 12, "roleAdmin", PRINCIPAL_.join("role/index-rich.jsf"));

    static MenuNode modifyPassword = entry(SEMFrameMenu.CONTROL, 50, "modifyPassword",
            PRINCIPAL_.join("modifyPassword.jsf"));

}
