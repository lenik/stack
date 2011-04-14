package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;
import javax.servlet.http.HttpServletRequest;

public class StyleChunk
        extends ScriptElement {

    @Override
    protected void formatHeader(HttpServletRequest req, IIndentedOut out)
            throws IOException {
        out.println("<style text='text/css'>");
    }

    @Override
    protected void formatFooter(HttpServletRequest req, IIndentedOut out)
            throws IOException {
        out.println("</style>");
    }

}
