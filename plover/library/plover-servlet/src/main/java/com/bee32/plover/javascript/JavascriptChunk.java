package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;

public class JavascriptChunk
        extends ScriptElement {

    @Override
    public void formatHeader(IIndentedOut out) {
        out.println("<script language='javascript'><!-- /* <![CDATA[ */");
    }

    @Override
    protected void formatFooter(IIndentedOut out)
            throws IOException {
        out.println("\n/* ]]> */ --></script>");
    }

}
