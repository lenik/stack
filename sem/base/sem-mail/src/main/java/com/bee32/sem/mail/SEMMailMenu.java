package com.bee32.sem.mail;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMailMenu
        extends MenuContribution {

    static ILocationContext MAIL_ = WEB_APP.join(SEMMailModule.PREFIX + "/");

    public static MenuNode MAIL = menu(SEMFrameMenu.START, 20, "mail");
    public static MenuNode SETTINGS = menu(MAIL, 100, "settings");

    static MenuNode folder = entry(SETTINGS, 10, "mailbox", MAIL_.join("folder/index.do"));
    static MenuNode filter = entry(SETTINGS, 20, "filter", MAIL_.join("filter/index.do"));

    static MenuNode compose = entry(MAIL, 0, "compose", MAIL_.join("mail/compose.do"));
    static MenuNode inbox = entry(MAIL, 100, "inbox", MAIL_.join("mailbox/inbox.do"));
    static MenuNode outbox = entry(MAIL, 200, "outbox", MAIL_.join("mailbox/outbox.do"));
    static MenuNode trash = entry(MAIL, 300, "trash", MAIL_.join("mailbox/trash.do"));

    @Override
    protected void preamble() {
        MAIL.setFlags(MenuNode.HIDDEN);
    }

}
