package com.bee32.sem.frame.ui;

import javax.faces.component.FacesComponent;
import javax.faces.component.UISelectMany;

@FacesComponent("com.bee32.sem.frame.ui.TagsSwitcher")
public class TagsSwitcher
        extends UISelectMany {

    static public final String COMPONENT_FAMILY = "javax.faces.SelectMany";

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

}
