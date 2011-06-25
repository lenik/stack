package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class ContextMenus
        extends MenuContribution {

    String CONTEXT = ".context";

    String FILE = CONTEXT + "/file";
    String VIEW = CONTEXT + "/view";
    String BUSINESS = CONTEXT + "/business";
    String NETWORK = CONTEXT + "/network";
    String OPTIONS = CONTEXT + "/options";
    String HELP = CONTEXT + "/help";

    MenuEntry FILE_READ = new MenuEntry("file.read", 1);
    MenuEntry FILE_WRITE = new MenuEntry("file.write", 2);
    MenuEntry FILE_ATTR = new MenuEntry("file.attr", 3);
    MenuEntry FILE_LOG = new MenuEntry("file.log", 4);

    MenuEntry VIEW_STYLE = new MenuEntry("view.style", 1);
    MenuEntry VIEW_LAYOUT = new MenuEntry("view.layout", 2);
    MenuEntry VIEW_LANG = new MenuEntry("view.lang", 3);

    MenuEntry BIZ_TICKET = new MenuEntry("business.ticket", 1);
    MenuEntry BIZ_REF = new MenuEntry("business.ref", 2);
    MenuEntry BIZ_PARENT = new MenuEntry("business.parent", 3);
    MenuEntry BIZ_CHILDREN = new MenuEntry("business.children", 4);
    MenuEntry BIZ_COMMENTS = new MenuEntry("business.comments", 5);
    MenuEntry BIZ_ATTR = new MenuEntry("business.attr", 6);

    MenuEntry NET_SEND = new MenuEntry("network.send", 1);
    MenuEntry NET_FORWARD = new MenuEntry("network.forward", 2);
    MenuEntry NET_SEARCH = new MenuEntry("network.search", 3);

    @Override
    protected void preamble() {
    }

}
