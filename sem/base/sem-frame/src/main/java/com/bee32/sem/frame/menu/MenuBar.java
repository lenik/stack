package com.bee32.sem.frame.menu;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.IllegalUsageException;

public class MenuBar
        extends MenuNode {

    public MenuBar(String name) {
        super(name);
    }

    protected void merge(MenuContribution menuContribution) {

        List<Entry<String, IMenuEntry>> dumped = menuContribution.dump();

        for (Map.Entry<String, ? extends IMenuEntry> contribElement : dumped) {
            String targetPath = contribElement.getKey();
            IMenuEntry menuEntry = contribElement.getValue();

            checkDup(targetPath + "/" + menuEntry.getName(), menuContribution);

            if (menuEntry instanceof IMenuNode) {
                IMenuNode menuNode = (IMenuNode) menuEntry;
                this.resolveMerge(targetPath, menuNode);
            } else {
                this.resolveMerge(targetPath, menuEntry);
            }
        }

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
