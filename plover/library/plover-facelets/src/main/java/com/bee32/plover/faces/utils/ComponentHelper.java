package com.bee32.plover.faces.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;

public class ComponentHelper {

    private final UIComponent facesComponent;

    public ComponentHelper(UIComponent facesComponent) {
        if (facesComponent == null)
            throw new NullPointerException("facesComponent");
        this.facesComponent = facesComponent;
    }

    /**
     * Get the rendered state of the component.
     */
    public boolean isRendered() {
        return facesComponent.isRendered();
    }

    /**
     * Set the rendered state of the component.
     */
    public void setRendered(boolean rendered) {
        facesComponent.setRendered(rendered);
    }

    /**
     * Do nothing if the corresponding component don't have a enabled attribute.
     */
    public boolean isEnabled() {
        if (facesComponent instanceof HtmlCommandButton) {
            HtmlCommandButton htmlButton = (HtmlCommandButton) facesComponent;
            return !htmlButton.isDisabled();
        }
        return false;
    }

    /**
     * Do nothing if the corresponding component don't have a enabled attribute.
     */
    public void setEnabled(boolean enabled) {
        if (facesComponent instanceof HtmlCommandButton) {
            HtmlCommandButton htmlButton = (HtmlCommandButton) facesComponent;
            htmlButton.setDisabled(!enabled);
        }
    }

}
