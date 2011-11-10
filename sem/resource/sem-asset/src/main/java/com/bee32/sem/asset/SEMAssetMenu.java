package com.bee32.sem.asset;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAssetMenu
        extends MenuContribution {

    static Location ASSET_ = WEB_APP.join(SEMAssetModule.PREFIX_);

    public static MenuNode ASSET = menu(SEMFrameMenu.BIZ1, "asset");

    static MenuNode accountTitleAdmin = entry(ASSET, 1, "accountTitleAdmin", ASSET_.join("accountTitle/"));

    @Override
    protected void preamble() {
    }

}
