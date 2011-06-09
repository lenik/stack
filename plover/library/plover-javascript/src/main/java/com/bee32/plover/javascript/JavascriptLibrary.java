package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.context.ILocationContext;
import com.bee32.plover.servlet.util.ThreadServletContext;

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
    protected void formatHeader(IIndentedOut out)
            throws IOException {
        HttpServletRequest req = ThreadServletContext.requireRequest();
        out.print("<script language='javascript' src='" + location.resolve(req) + "'>");
    }

    @Override
    protected void formatFooter(IIndentedOut out)
            throws IOException {
        out.println("</script>");
    }

}
