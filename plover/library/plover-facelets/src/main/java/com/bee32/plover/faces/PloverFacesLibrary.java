package com.bee32.plover.faces;

import com.bee32.plover.faces.component.Locker;
import com.bee32.plover.faces.component.SelectOneObjectMenu;
import com.bee32.plover.faces.primefaces.PloverPush;
import com.bee32.plover.faces.tag.DisableHandler;

public class PloverFacesLibrary
        extends _TagLibrary {

    public static final String NAMESPACE = "http://bee32.com/plover/faces";

    public PloverFacesLibrary() {
        super(NAMESPACE);

        addComponent("select", SelectOneObjectMenu.COMPONENT_TYPE, SelectOneObjectMenu.DEFAULT_RENDERER_TYPE);

        addTagHandler("disable", DisableHandler.class); // Not used.
        addComponent("locker", Locker.COMPONENT_TYPE, Locker.DEFAULT_RENDERER_TYPE);

        // PrimeFaces enhancements.
        addComponent("push", PloverPush.COMPONENT_TYPE, PloverPush.DEFAULT_RENDERER_TYPE);
    }

}
