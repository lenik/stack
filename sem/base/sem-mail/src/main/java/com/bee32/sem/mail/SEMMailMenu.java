package com.bee32.sem.mail;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMMailMenu
        extends MenuContribution {

    static ILocationContext MAIL = WEB_APP.join(SEMMailModule.PREFIX + "/");

    @Contribution("sa")
    MenuEntry mailAdmin = new MenuEntry("mailadmin");

    @Contribution("sa/mailadmin")
    MenuEntry mailbox = new MenuEntry(10, "mailbox", MAIL.join("mailbox/index.do"));

    @Contribution("sa/mailadmin")
    MenuEntry filter = new MenuEntry(20, "filter", MAIL.join("filter/index.do"));

    @Contribution(".")
    MenuEntry mail = new MenuEntry("mail");

    @Contribution("mail")
    MenuEntry compose = new MenuEntry(0, "compose", MAIL.join("mail/compose.do"));

    @Contribution("mail")
    MenuEntry inbox = new MenuEntry(100, "inbox", MAIL.join("mailbox/inbox.do"));

    @Contribution("mail")
    MenuEntry outbox = new MenuEntry(200, "outbox", MAIL.join("mailbox/outbox.do"));

    @Contribution("mail")
    MenuEntry trash = new MenuEntry(300, "trash", MAIL.join("mailbox/trash.do"));

    @Override
    protected void preamble() {
    }

}
