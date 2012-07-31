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

    protected SimpleForm simpleForm(String actionHrefText) {
        SimpleForm simpleForm = new SimpleForm(out, actionHrefText);
        return simpleForm;
    }

    protected SimpleRow simpleRow(String name, Object value) {
        SimpleRow simpleRow = new SimpleRow(out);
        simpleRow.put(name, value);
        return simpleRow;
    }

    protected SimpleRow simpleRow(String name, Object value, String comment) {
        SimpleRow simpleRow = new SimpleRow(out);
        simpleRow.put(name, value, comment);
        return simpleRow;
    }

    protected SimpleRowImage simpleRowImage(String name, Location image, Location href) {
        SimpleRowImage simpleRowImage = new SimpleRowImage(out);
        simpleRowImage.put(name, image, href);
        return simpleRowImage;
    }

}
