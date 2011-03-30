package com.bee32.sem.frame.menu;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import com.bee32.plover.arch.ui.IAppearance;

public class MenuContributionTest {

    @Test
    public void test1() {
        MyMenu menu = new MyMenu();
        List<Entry<String, IMenuEntry>> dump = menu.dump();

        Entry<String, IMenuEntry> entry = dump.iterator().next();

        String targetPath = entry.getKey();
        assertEquals("menu.file", targetPath);

        IMenuEntry menuItem = entry.getValue();

        IAppearance appearance = menuItem.getAppearance();
        String displayName = appearance.getDisplayName();
        String description = appearance.getDescription();

        assertEquals("Open File", displayName);
        assertEquals("Browse and open a file", description);
    }

}
