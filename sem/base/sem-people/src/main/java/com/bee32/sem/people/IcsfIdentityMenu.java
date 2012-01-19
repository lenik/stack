package com.bee32.sem.people;

import com.bee32.icsf.principal.IcsfPrincipalModule;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfIdentityMenu
        extends MenuContribution {

    static Location IDENTITY_ = WEB_APP.join(IcsfPrincipalModule.PREFIX_);

    public static MenuNode identityMenu = menu(SEMFrameMenu.SYSTEM, 1, "identity");

    static MenuNode roleAdmin = entry(identityMenu, 100, "roleAdmin", IDENTITY_.join("role/index-rich.jsf"));
    static MenuNode groupAdmin = entry(identityMenu, 110, "groupAdmin", IDENTITY_.join("group/index-rich.jsf"));
    static MenuNode userAdmin = entry(identityMenu, 120, "userAdmin", IDENTITY_.join("user/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
