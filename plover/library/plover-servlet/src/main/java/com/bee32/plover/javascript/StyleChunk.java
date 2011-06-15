package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;

public class StyleChunk
        extends ScriptElement {

    @Override
    protected void formatHeader(IIndentedOut out)
            throws IOException {
        out.println("<style text='text/css'>");
    }

    @Override
    protected void formatFooter(IIndentedOut out)
            throws IOException {
        out.println("</style>");
    }

}
