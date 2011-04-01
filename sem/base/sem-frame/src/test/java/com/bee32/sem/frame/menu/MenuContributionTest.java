package com.bee32.sem.frame.menu;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.bee32.plover.arch.ui.IAppearance;

public class MenuContributionTest {

    @Test
    public void test1() {

        MyMenu menu = new MyMenu();
        List<Entry<String, IMenuEntry>> dump = menu.dump();

        Map<String, IMenuEntry> map = new HashMap<String, IMenuEntry>();
        for (Entry<String, IMenuEntry> entry : dump)
            map.put(entry.getKey(), entry.getValue());

        IMenuEntry firstMenu = map.get(".");

        IAppearance appearance = firstMenu.getAppearance();
        String displayName = appearance.getDisplayName();
        String description = appearance.getDescription();

        assertEquals("File", displayName);
        assertEquals("File operations", description);
    }

}
