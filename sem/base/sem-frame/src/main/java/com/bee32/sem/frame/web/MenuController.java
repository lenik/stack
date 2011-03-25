package com.bee32.sem.frame.web;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Menu;

import com.bee32.sem.frame.builtins.MainMenu;
import com.bee32.sem.frame.menu.ZkMenuBuilder;

/**
 * @see ZkMenuBuilder
 */
@Controller
@Lazy
public class MenuController
        extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;

    @Inject
    private MainMenu mainMenu;

    private Menu menubar;

    @Override
    public void doAfterCompose(Component comp)
            throws Exception {
        super.doAfterCompose(comp);

        menubar = ZkMenuBuilder.buildMenu(mainMenu);
    }

}
