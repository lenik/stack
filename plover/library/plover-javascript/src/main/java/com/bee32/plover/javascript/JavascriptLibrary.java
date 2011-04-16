package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.context.ILocationContext;

public class JavascriptLibrary
        extends ScriptElement {

    ILocationContext location;

    public JavascriptLibrary(ILocationContext location) {
        if (location == null)
            throw new NullPointerException("location");
    }

    public ILocationContext getLocation() {
        return location;
    }

    public void setLocation(ILocationContext location) {
        this.location = location;
    }

    @Override
    protected void formatHeader(HttpServletRequest req, IIndentedOut out)
            throws IOException {
        out.print("<script language='javascript' src='" + location.resolve(req) + "'>");
    }

    @Override
    protected void formatFooter(HttpServletRequest req, IIndentedOut out)
            throws IOException {
        out.println("</script>");
    }

}
