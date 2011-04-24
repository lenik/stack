package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;
import javax.servlet.http.HttpServletRequest;

public class JavascriptChunk
        extends ScriptElement {

    @Override
    public void formatHeader(HttpServletRequest req, IIndentedOut out) {
        out.println("<script language='javascript'><!-- /* <![CDATA[ */");
    }

    @Override
    protected void formatFooter(HttpServletRequest req, IIndentedOut out)
            throws IOException {
        out.println("\n/* ]]> */ --></script>");
    }

}
