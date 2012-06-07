package com.bee32.sem.frame.web;

import org.primefaces.model.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.PrimefacesMenuBuilder;

@Component
@Scope("request")
public class PrimefacesMenuBean {

    PrimefacesMenuBuilder builder = new PrimefacesMenuBuilder();

    public MenuModel getModel() {
        MenuNode root = null;
        MenuModel menubar = builder.buildMenubar(root);
        return menubar;
    }

}
