package com.bee32.sem.mail;

import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMMailMenu
        extends MenuContribution {

    @Contribution(".")
    MenuEntry mail = new MenuEntry("mail");

    @Contribution("mail")
    MenuEntry compose = new MenuEntry(0, "compose");

    @Contribution("mail")
    MenuEntry inbox = new MenuEntry(100, "inbox");

    @Contribution("mail")
    MenuEntry outbox = new MenuEntry(200, "outbox");

    @Contribution("mail")
    MenuEntry trash = new MenuEntry(300, "trash");

    @Override
    protected void preamble() {
    }

}
