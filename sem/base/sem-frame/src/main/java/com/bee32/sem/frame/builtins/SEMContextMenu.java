package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMContextMenu
        extends MenuComposite {

    public MenuNode CONTEXT = menu("context");

    public MenuNode FILE = menu(CONTEXT, 10, "file");
    public MenuNode VIEW = menu(CONTEXT, 10, "view");
    public MenuNode BIZ = menu(CONTEXT, 10, "biz");
    public MenuNode NETWORK = menu(CONTEXT, 10, "network");
    public MenuNode OPTIONS = menu(CONTEXT, 10, "options");
    public MenuNode HELP = menu(CONTEXT, 10, "help");

    public MenuNode FILE_READ = section(FILE, 10, ":read");
    public MenuNode FILE_WRITE = section(FILE, 20, ":write");
    public MenuNode FILE_ATTR = section(FILE, 30, ":attr");
    public MenuNode FILE_LOG = section(FILE, 40, ":log");

    public MenuNode VIEW_STYLE = section(VIEW, 10, ":style");
    public MenuNode VIEW_LAYOUT = section(VIEW, 20, ":layout");
    public MenuNode VIEW_LANG = section(VIEW, 30, ":lang");

    public MenuNode BIZ_TICKET = section(BIZ, 10, ":ticket");
    public MenuNode BIZ_REF = section(BIZ, 20, ":ref");
    public MenuNode BIZ_PARENT = section(BIZ, 30, ":parent");
    public MenuNode BIZ_CHILDREN = section(BIZ, 40, ":children");
    public MenuNode BIZ_COMMENTS = section(BIZ, 50, ":comments");
    public MenuNode BIZ_ATTR = section(BIZ, 60, ":attr");

    public MenuNode NET_SEND = section(NETWORK, 10, ":send");
    public MenuNode NET_FORWARD = section(NETWORK, 20, ":forward");
    public MenuNode NET_SEARCH = section(NETWORK, 30, ":search");

}
