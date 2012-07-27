package com.bee32.plover.html;

import java.io.Writer;

import com.bee32.plover.rtx.location.Location;

public class HtmlTemplateForForm
        extends HtmlTemplate {

    public HtmlTemplateForForm() {
        super();
    }

    public HtmlTemplateForForm(Writer writer) {
        super(writer);
    }

    protected HtmlTemplateForForm simpleForm(String action, Object... array) {
        new SimpleForm(out).create(action, array);
        return this;
    }

    protected HtmlTemplateForForm simpleRow(String name, Object value) {
        new SimpleRow(out).put(name, value);
        return this;
    }

    protected HtmlTemplateForForm simpleRow(String name, Object value, String comment) {
        new SimpleRow(out).put(name, value, comment);
        return this;
    }

    protected HtmlTemplateForForm simpleRowImage(String name, Location image, Location href) {
        new SimpleRowImage(out).put(name, image, href);
        return this;
    }

}
