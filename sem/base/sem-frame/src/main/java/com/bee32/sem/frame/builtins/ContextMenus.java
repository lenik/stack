package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class ContextMenus
        extends MenuContribution {

    public static final MenuNode CONTEXT = menu("context");

    public static final MenuNode FILE = menu(CONTEXT, 10, "file");
    public static final MenuNode VIEW = menu(CONTEXT, 10, "view");
    public static final MenuNode BIZ = menu(CONTEXT, 10, "biz");
    public static final MenuNode NETWORK = menu(CONTEXT, 10, "network");
    public static final MenuNode OPTIONS = menu(CONTEXT, 10, "options");
    public static final MenuNode HELP = menu(CONTEXT, 10, "help");

    public static final MenuNode FILE_READ = section(FILE, 10, "file.read");
    public static final MenuNode FILE_WRITE = section(FILE, 20, "file.write");
    public static final MenuNode FILE_ATTR = section(FILE, 30, "file.attr");
    public static final MenuNode FILE_LOG = section(FILE, 40, "file.log");

    public static final MenuNode VIEW_STYLE = section(VIEW, 10, "view.style");
    public static final MenuNode VIEW_LAYOUT = section(VIEW, 20, "view.layout");
    public static final MenuNode VIEW_LANG = section(VIEW, 30, "view.lang");

    public static final MenuNode BIZ_TICKET = section(BIZ, 10, "biz.ticket");
    public static final MenuNode BIZ_REF = section(BIZ, 20, "biz.ref");
    public static final MenuNode BIZ_PARENT = section(BIZ, 30, "biz.parent");
    public static final MenuNode BIZ_CHILDREN = section(BIZ, 40, "biz.children");
    public static final MenuNode BIZ_COMMENTS = section(BIZ, 50, "biz.comments");
    public static final MenuNode BIZ_ATTR = section(BIZ, 60, "biz.attr");

    public static final MenuNode NET_SEND = section(NETWORK, 10, "net.send");
    public static final MenuNode NET_FORWARD = section(NETWORK, 20, "net.forward");
    public static final MenuNode NET_SEARCH = section(NETWORK, 30, "net.search");

    @Override
    protected void preamble() {
    }

}
