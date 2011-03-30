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
            IMenuEntry menuEntry = contribElement.getValue();

            if (menuEntry instanceof IMenuNode) {
                IMenuNode menuNode = (IMenuNode) menuEntry;
                if (!this.resolveMerge(targetPath, menuNode))
                    throw new IllegalUsageException("Duplicated menu node: " + menuNode);
            } else {
                if (!this.resolveMerge(targetPath, menuEntry))
                    throw new IllegalUsageException("Duplicated menu item: " + menuEntry);
            }
        }

    }

}
