package com.bee32.sem.frame.web;

import javax.inject.Inject;

import org.primefaces.model.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuLoader;
import com.bee32.sem.frame.menu.PrimefacesMenuBuilder;

@Component
@Scope("request")
public class PrimefacesMenuBean {

    /**
     * Inject NLS.
     */
    @Inject
    MenuLoader menuLoader;

    PrimefacesMenuBuilder builder = PrimefacesMenuBuilder.INSTANCE;

    public MenuModel getModel() {
        MenuModel model = builder.buildMenubar(SEMFrameMenu.MAIN);
        return model;
    }

}
