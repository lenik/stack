package com.bee32.sem.frame.menu;

import java.util.IdentityHashMap;

import javax.free.IllegalUsageException;

public class MenuModel
        extends MenuNode {

    public MenuModel(String name) {
        super(name);
    }

    private IdentityHashMap<String, MenuContribution> srcmap = new IdentityHashMap<String, MenuContribution>();

    protected void checkDup(String path, MenuContribution contrib) {
        MenuContribution last = srcmap.get(path);
        if (last != null)
            throw new IllegalUsageException(String.format(DUP_MENU_DEF, path, last));
        srcmap.put(path, contrib);
    }

    static String DUP_MENU_DEF = "Duplicated menu path %s, last occurred in %s.";

}
