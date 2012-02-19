package com.bee32.sem.frame.menu;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.bee32.plover.arch.ui.IAppearance;

public class MenuContributionTest {

    @Test
    public void testDump() {

        MyMenu menu = new MyMenu();
        Map<String, MenuNode> map = menu.prepareMap();

        IMenuEntry firstMenu = map.get("FILE");

        IAppearance appearance = firstMenu.getAppearance();
        String displayName = appearance.getDisplayName();
        String description = appearance.getDescription();

        assertEquals("File", displayName);
        assertEquals("File operations", description);
    }

}
