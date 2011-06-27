package com.bee32.sem.mail;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.SEMMainMenu;

public class SEMMailMenu
        extends MenuContribution {

    static ILocationContext MAIL = WEB_APP.join(SEMMailModule.PREFIX + "/");

    public static MenuNode mailAdmin = menu(SEMMainMenu.ADMIN, "mailadmin");
    public static MenuNode mail = menu(SEMMainMenu.MAIN, "mail");

    static MenuNode mailbox = entry(mailAdmin, 10, "mailbox", MAIL.join("mailbox/index.do"));
    static MenuNode filter = entry(mailAdmin, 20, "filter", MAIL.join("filter/index.do"));

    static MenuNode compose = entry(mail, 0, "compose", MAIL.join("mail/compose.do"));
    static MenuNode inbox = entry(mail, 100, "inbox", MAIL.join("mailbox/inbox.do"));
    static MenuNode outbox = entry(mail, 200, "outbox", MAIL.join("mailbox/outbox.do"));
    static MenuNode trash = entry(mail, 300, "trash", MAIL.join("mailbox/trash.do"));

    @Override
    protected void preamble() {
    }

}
