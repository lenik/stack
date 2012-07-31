package com.bee32.plover.html;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * array: each entry as { name, label, value }
 *
 * action may be in format "href:action-text"
 *
 * name may be prefixed with `-` for read-only fields.
 *
 * label may be in format "LABEL:HELPDOC", where HELPDOC is shown as control title.
 */
public class SimpleForm
        extends HtmlSugar {

    String actionText;
    String actionHref;
    List<FormEntry> entries = new ArrayList<FormEntry>();

    public SimpleForm(String action) {
        this(new StringWriter(), action);
    }

    public SimpleForm(Writer writer, String hrefText) {
        super(writer);
        int actionColon = hrefText.indexOf(':');
        if (actionColon != -1) {
            actionHref = hrefText.substring(0, actionColon);
            actionText = hrefText.substring(actionColon + 1);
        } else {
            actionHref = hrefText;
            actionText = "保存";
        }
    }

    public SimpleForm addEntry(String name, String labelTip, Object value) {
        FormEntry entry = new FormEntry(name, labelTip, value);
        return addEntry(entry);
    }

    public SimpleForm addEntry(FormEntry entry) {
        entries.add(entry);
        return this;
    }

    public SimpleForm addSection(String name) {
        FormEntry entry = new FormEntry("-", name, null);
        return addEntry(entry);
    }

    public SimpleForm render() {
        beginForm();
        beginTable();
        renderEntries();
        endTable();
        endForm();
        return this;
    }

    public SimpleForm renderEntries() {
        for (FormEntry entry : entries) {
            entry.render(this, getRequest());
        }
        return this;
    }

    public SimpleForm beginForm() {
        form().method("get").action(actionHref);
        return this;
    }

    public SimpleForm beginTable() {
        table().classAttr("tcf").border("0");
        return this;
    }

    public SimpleForm endTable() {
        tr().td().colspan("3").align("left");
        input().type("submit").name("save").value(actionText).end();
        text("");
        input().type("reset").value("重置").end();
        end(3); // .table.tr.td
        return this;
    }

    public SimpleForm endForm() {
        end();
        return this;
    }

}
