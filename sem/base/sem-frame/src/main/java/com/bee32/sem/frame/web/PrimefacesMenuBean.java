package com.bee32.sem.frame.web;

import org.primefaces.model.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.PrimefacesMenuBuilder;

@Component
@Scope("request")
public class PrimefacesMenuBean {

    PrimefacesMenuBuilder builder = PrimefacesMenuBuilder.INSTANCE;

    public MenuModel getModel() {
        MenuModel model = builder.buildMenubar(SEMFrameMenu.getMainMenu());
        return model;
    }

}
