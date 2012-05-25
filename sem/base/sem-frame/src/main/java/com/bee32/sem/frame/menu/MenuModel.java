package com.bee32.sem.frame.menu;

import java.util.IdentityHashMap;

import javax.free.IllegalUsageException;

public class MenuModel
        extends MenuNode {

    public MenuModel(String name) {
        super(name);
    }

    private IdentityHashMap<String, MenuComposite> srcmap = new IdentityHashMap<String, MenuComposite>();

    protected void checkDup(String path, MenuComposite comp) {
        MenuComposite last = srcmap.get(path);
        if (last != null)
            throw new IllegalUsageException(String.format(DUP_MENU_DEF, path, last));
        srcmap.put(path, comp);
    }

    static String DUP_MENU_DEF = "Duplicated menu path %s, last occurred in %s.";

}
