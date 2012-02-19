package com.bee32.plover.faces.component;

import org.primefaces.component.selectonemenu.SelectOneMenu;

public class SelectOneObjectMenu
        extends SelectOneMenu {

    public static final String COMPONENT_TYPE = "plover.faces.SelectOneObjectMenu";
    public static final String DEFAULT_RENDERER_TYPE = "org.primefaces.component.SelectOneMenuRenderer";

    protected enum PropertyKeys {
        target,
    }

    public Object getTarget() {
        return getStateHelper().eval(PropertyKeys.target);
    }

    public void setTarget(String target) {
        getStateHelper().put(PropertyKeys.target, target);
        handleAttribute("target", target);
    }

}
