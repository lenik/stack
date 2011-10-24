package com.bee32.plover.site;

import java.io.StringWriter;
import java.io.Writer;

import com.googlecode.jatl.Html;

/**
 * Features:
 *
 * <ul>
 * <li>Wrapped Html but exports the writer, so toString() would work.
 * <li>Add more html sugar constructors.
 * </ul>
 */
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
