package com.bee32.sem.uber;

import java.util.Locale;

import javax.faces.component.UIComponent;

import org.junit.Test;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.MenuModel;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.PrimefacesMenuBuilder;

public class SEMUberMenuTest
        extends WiredTestCase {

    static {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
    }

    @Test
    public void testDump() {
        System.out.println(SEMFrameMenu.getMainMenu().toString());
    }

    // @Test
    public void testPrimefacesMenuBuilder() {
        PrimefacesMenuBuilder mb = PrimefacesMenuBuilder.INSTANCE;
        MenuModel menubar = mb.buildMenubar(SEMFrameMenu.getMainMenu());
        for (UIComponent m : menubar.getContents()) {
            Submenu submenu = (Submenu) m;
            System.out.println(submenu.getLabel());
        }
    }

}
