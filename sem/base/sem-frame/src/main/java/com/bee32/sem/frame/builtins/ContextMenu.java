package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuSection;

public interface ContextMenu {

    String CONTEXT_FILE = "context.file";
    String CONTEXT_VIEW = "context.view";
    String CONTEXT_BUSINESS = "context.business";
    String CONTEXT_NETWORK = "context.network";
    String CONTEXT_OPTIONS = "context.options";
    String CONTEXT_HELP = "context.help";

    MenuSection FILE_READ = new MenuSection("file.read", 1);
    MenuSection FILE_WRITE = new MenuSection("file.write", 2);
    MenuSection FILE_ATTR = new MenuSection("file.write", 3);
    MenuSection FILE_LOG = new MenuSection("file.write", 4);

    MenuSection VIEW_STYLE = new MenuSection("view.style", 1);
    MenuSection VIEW_LAYOUT = new MenuSection("view.layout", 2);
    MenuSection VIEW_LANG = new MenuSection("view.lang", 3);

    MenuSection BUSINESS_TICKET = new MenuSection("business.ticket", 1);
    MenuSection BUSINESS_REF = new MenuSection("business.ref", 2);
    MenuSection BUSINESS_PARENT = new MenuSection("business.parent", 3);
    MenuSection BUSINESS_CHILDREN = new MenuSection("business.children", 4);
    MenuSection BUSINESS_COMMENTS = new MenuSection("business.comments", 5);
    MenuSection BUSINESS_ATTR = new MenuSection("business.attr", 6);

    MenuSection NETWORK_SEND = new MenuSection("network.send", 1);
    MenuSection NETWORK_FORWARD = new MenuSection("network.forward", 2);
    MenuSection NETWORK_SEARCH = new MenuSection("network.search", 3);

}
