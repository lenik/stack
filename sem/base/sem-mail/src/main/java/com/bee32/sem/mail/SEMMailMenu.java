package com.bee32.sem.mail;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMailMenu
        extends MenuComposite {

    static ILocationContext __ = WEB_APP.join(SEMMailModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode MAIL = menu(_frame_.START, 20, "MAIL");
    public MenuNode SETTINGS = menu(MAIL, 100, "SETTINGS");

    MenuNode folder = entry(SETTINGS, 10, "mailbox", __.join("folder/index.do"));
    MenuNode filter = entry(SETTINGS, 20, "filter", __.join("filter/index.do"));

    MenuNode compose = entry(MAIL, 0, "compose", __.join("mail/compose.do"));
    MenuNode inbox = entry(MAIL, 100, "inbox", __.join("mailbox/inbox.do"));
    MenuNode outbox = entry(MAIL, 200, "outbox", __.join("mailbox/outbox.do"));
    MenuNode trash = entry(MAIL, 300, "trash", __.join("mailbox/trash.do"));

    @Override
    protected void preamble() {
        MAIL.setFlags(MenuNode.HIDDEN);
    }

}
