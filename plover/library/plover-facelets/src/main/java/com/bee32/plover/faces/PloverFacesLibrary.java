package com.bee32.plover.faces;

import org.primefaces.component.selectonemenu.SelectOneMenuRenderer;

import com.bee32.plover.faces.component.SelectOneObjectMenu;

public class PloverFacesLibrary
        extends _TagLibrary {

    public static final String NAMESPACE = "http://bee32.com/plover/faces";

    public PloverFacesLibrary() {
        super(NAMESPACE);

        // addTagHandler("select", SelectHandler);
        addComponent("select", SelectOneObjectMenu.class.getCanonicalName(),
                SelectOneMenuRenderer.class.getCanonicalName());
    }

}
