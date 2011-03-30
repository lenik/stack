package com.bee32.sem.frame.menu;

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
            IMenuEntry menuItem = contribElement.getValue();

            if (menuItem instanceof IMenuNode) {
                IMenuNode menuEntry = (IMenuNode) menuItem;
                if (!this.resolveMerge(targetPath, menuEntry))
                    throw new IllegalUsageException("Duplicated menu entry: " + menuEntry);
            } else {
                if (!this.resolveMerge(targetPath, menuItem))
                    throw new IllegalUsageException("Duplicated menu item: " + menuItem);
            }
        }

    }

}
