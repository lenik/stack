package com.bee32.sem.asset;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAssetMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAssetModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode ASSET = menu(_frame_.MAIN, 410, "ASSET");

    MenuNode accountSubjectAdmin = entry(ASSET, 1, "accountSubjectAdmin", __.join("subject/"));

    MenuNode accountInit = entry(ASSET, 2, "accountInit", __.join("init/"));

    MenuNode creditNoteAdmin = entry(ASSET, 3, "creditNoteAdmin", __.join("creditNote/"));
    MenuNode paymentNoteAdmin = entry(ASSET, 4, "paymentNoteAdmin", __.join("paymentNote/"));
    MenuNode accountTicketAdmin = entry(ASSET, 5, "accountTicketAdmin", __.join("ticket/"));

    MenuNode assetQuery = entry(ASSET, 7, "assetQuery", __.join("query/"));
}
