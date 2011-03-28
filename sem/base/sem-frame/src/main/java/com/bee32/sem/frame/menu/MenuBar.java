package com.bee32.sem.frame.menu;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MenuBar
        extends MenuEntry {

    public MenuBar(String name) {
        super(name);
    }

    protected void merge(MenuContribution menuContribution) {

        List<Entry<String, IMenuItem>> dumped = menuContribution.dump();

        for (Map.Entry<String, ? extends IMenuItem> contribElement : dumped) {
            String targetPath = contribElement.getKey();
            IMenuItem menuItem = contribElement.getValue();

            if (menuItem instanceof IMenuEntry) {
                IMenuEntry menuEntry = (IMenuEntry) menuItem;
                this.resolveMerge(targetPath, menuEntry);
            } else {
                this.resolveMerge(targetPath, menuItem);
            }
        }

    }

}
