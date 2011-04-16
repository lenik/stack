package com.bee32.sem.frame.web;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Menubar;

import com.bee32.plover.servlet.util.ThreadServletContext;
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

        // XXX - Zk Composer 怎么获取有用的 ServletRequest?
        HttpServletRequest request = ThreadServletContext.requireRequest();

        new ZkMenuBuilder(mainMenu, menubar, request).buildMenubar();
    }

}
