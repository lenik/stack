package com.bee32.plover.faces.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent("plover.logo")
public class UILogo
        extends UIInput {

    @Override
    public String getFamily() {
        return "custom";
    }

    @Override
    public void encodeEnd(FacesContext context)
            throws IOException {
        ResponseWriter out = context.getResponseWriter();
        out.startElement("div", this);
        out.write("A plover-facelets presentation.");
        out.endElement("div");
    }

}
