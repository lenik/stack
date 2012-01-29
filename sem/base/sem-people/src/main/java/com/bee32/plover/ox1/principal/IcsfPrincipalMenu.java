package com.bee32.plover.ox1.principal;

import com.bee32.icsf.principal.IcsfPrincipalModule;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class IcsfPrincipalMenu
        extends MenuContribution {

    static Location PRINCIPAL_ = WEB_APP.join(IcsfPrincipalModule.PREFIX_);

    public static MenuNode identityMenu = menu(SEMFrameMenu.SYSTEM, 1, "identity");

    static MenuNode roleAdmin = entry(identityMenu, 100, "roleAdmin", PRINCIPAL_.join("role/index-rich.jsf"));
    static MenuNode groupAdmin = entry(identityMenu, 110, "groupAdmin", PRINCIPAL_.join("group/index-rich.jsf"));
    static MenuNode userAdmin = entry(identityMenu, 120, "userAdmin", PRINCIPAL_.join("user/index-rich.jsf"));

    static MenuNode modifyPassword = entry(SEMFrameMenu.PREFERENCES, 9, "modifyPassword",
            PRINCIPAL_.join("modifyPassword.jsf"));

    @Override
    protected void preamble() {
    }

}
