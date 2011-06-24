package com.bee32.sem.frame.builtins;

public interface ContextMenus {

    String CONTEXT = "context";

    String File = CONTEXT + "/file";
    String View = CONTEXT + "/view";
    String Business = CONTEXT + "/business";
    String Network = CONTEXT + "/network";
    String Options = CONTEXT + "/options";
    String Help = CONTEXT + "/help";

    interface file {
        MenuSection READ = new MenuSection("file.read", 1);
        MenuSection WRITE = new MenuSection("file.write", 2);
        MenuSection ATTR = new MenuSection("file.attr", 3);
        MenuSection LOG = new MenuSection("file.log", 4);
    }

    interface view {
        MenuSection STYLE = new MenuSection("view.style", 1);
        MenuSection LAYOUT = new MenuSection("view.layout", 2);
        MenuSection LANG = new MenuSection("view.lang", 3);
    }

    interface business {
        MenuSection TICKET = new MenuSection("business.ticket", 1);
        MenuSection REF = new MenuSection("business.ref", 2);
        MenuSection PARENT = new MenuSection("business.parent", 3);
        MenuSection CHILDREN = new MenuSection("business.children", 4);
        MenuSection COMMENTS = new MenuSection("business.comments", 5);
        MenuSection ATTR = new MenuSection("business.attr", 6);
    }

    interface network {
        MenuSection SEND = new MenuSection("network.send", 1);
        MenuSection FORWARD = new MenuSection("network.forward", 2);
        MenuSection SEARCH = new MenuSection("network.search", 3);
    }

}
