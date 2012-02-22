package com.bee32.sem.uber;

import javax.faces.component.UIComponent;
import javax.inject.Inject;

import org.junit.Test;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.MenuModel;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuLoader;
import com.bee32.sem.frame.menu.PrimefacesMenuBuilder;

public class SEMUberMenuTest
        extends WiredTestCase {

    @Inject
    MenuLoader menuLoader;

    @Test
    public void testPrimefacesMenuBuilder() {
        PrimefacesMenuBuilder mb=PrimefacesMenuBuilder.INSTANCE;
        MenuModel menubar = mb.buildMenubar(SEMFrameMenu.MAIN);
        for (UIComponent m : menubar.getContents()) {
            Submenu submenu = (Submenu) m;
            System.out.println(submenu.getLabel());
        }
    }

}
