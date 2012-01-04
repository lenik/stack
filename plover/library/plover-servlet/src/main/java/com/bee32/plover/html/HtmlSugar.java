package com.bee32.plover.html;

import java.io.IOException;
import java.io.Writer;

public class HtmlSugar
        extends AbstractHtmlTemplate {

    public HtmlSugar() {
        super();
    }

    public HtmlSugar(Writer writer) {
        super(writer);
    }

    /**
     * For HTML sugar, the specific methods are used.
     */
    @Override
    protected void instantiate()
            throws IOException {
    }

}
