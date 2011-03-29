package com.bee32.sem.frame.web;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;

import com.bee32.sem.frame.builtins.MainMenu;
import com.bee32.sem.frame.menu.ZkMenuBuilder;

/**
 * @see ZkMenuBuilder
 */
public class MenuComposer
        extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;

    private MainMenu mainMenu;

    private Menubar menubar;

    @Override
    public void doAfterCompose(Component comp)
            throws Exception {
        super.doAfterCompose(comp);

        ZkMenuBuilder.buildMenubar(menubar, mainMenu);
    }

}
