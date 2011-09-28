package com.bee32.plover.site;

import java.io.StringWriter;
import java.io.Writer;

import com.googlecode.jatl.Html;

public class HtmlBuilder
        extends Html {

    Writer out;

    public HtmlBuilder() {
        this(new StringWriter());
    }

    public HtmlBuilder(Writer writer) {
        super(writer);
        this.out = writer;
    }

    @Override
    public String toString() {
        return out.toString();
    }

}
