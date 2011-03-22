package com.bee32.sem.frame.context;

import com.bee32.sem.frame.menu.MenuGroup;

public interface ContextMenu {

    String CONTEXT_FILE = "context.file";
    String CONTEXT_VIEW = "context.view";
    String CONTEXT_BUSINESS = "context.business";
    String CONTEXT_NETWORK = "context.network";
    String CONTEXT_OPTIONS = "context.options";
    String CONTEXT_HELP = "context.help";

    MenuGroup FILE_READ = new MenuGroup("file.read", 1);
    MenuGroup FILE_WRITE = new MenuGroup("file.write", 2);
    MenuGroup FILE_ATTR = new MenuGroup("file.write", 3);
    MenuGroup FILE_LOG = new MenuGroup("file.write", 4);

    MenuGroup VIEW_STYLE = new MenuGroup("view.style", 1);
    MenuGroup VIEW_LAYOUT = new MenuGroup("view.layout", 2);
    MenuGroup VIEW_LANG = new MenuGroup("view.lang", 3);

    MenuGroup BUSINESS_TICKET = new MenuGroup("business.ticket", 1);
    MenuGroup BUSINESS_REF = new MenuGroup("business.ref", 2);
    MenuGroup BUSINESS_PARENT = new MenuGroup("business.parent", 3);
    MenuGroup BUSINESS_CHILDREN = new MenuGroup("business.children", 4);
    MenuGroup BUSINESS_COMMENTS = new MenuGroup("business.comments", 5);
    MenuGroup BUSINESS_ATTR = new MenuGroup("business.attr", 6);

    MenuGroup NETWORK_SEND = new MenuGroup("network.send", 1);
    MenuGroup NETWORK_FORWARD = new MenuGroup("network.forward", 2);
    MenuGroup NETWORK_SEARCH = new MenuGroup("network.search", 3);

}
