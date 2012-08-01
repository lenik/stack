package com.bee32.plover.html;

import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

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
    Map<String, FormSection> sections = new LinkedHashMap<>();
    FormSection currentSection;

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
        currentSection = section("default");
    }

    public synchronized FormSection section(String sectionId) {
        String defaultTitle = null; // sectionId;
        return section(sectionId, defaultTitle);
    }

    public synchronized FormSection section(String sectionId, String defaultTitle) {
        FormSection section = sections.get(sectionId);
        if (section == null) {
            section = new FormSection(defaultTitle);
            sections.put(sectionId, section);
        }
        return currentSection = section;
    }

    public FormSection getCurrentSection() {
        return currentSection;
    }

    public SimpleForm addChild(IFormChild child) {
        getCurrentSection().add(child);
        return this;
    }

    public SimpleForm addEntry(String name, String labelTip, Object value) {
        FormEntry entry = new FormEntry(name, labelTip, value);
        return addChild(entry);
    }

    public SimpleForm render() {
        beginForm();
        renderEntries();
        endForm();
        return this;
    }

    public SimpleForm renderEntries() {
        for (String sectionId : sections.keySet()) {
            FormSection section = sections.get(sectionId);
            beginSection(section.getTitle());
            for (IFormChild child : section) {
                child.render(this, getRequest());
            }
            endSection();
        }

        return this;
    }

    public SimpleForm beginForm() {
        form().classAttr("tcf").method("post").action(actionHref);
        return this;
    }

    public SimpleForm endForm() {
        div().align("center");
        input().type("submit").name("save").value(actionText).end();
        text("");
        input().type("reset").value("重置").end();
        end();

        end();
        return this;
    }

    SimpleForm beginSection(String title) {
        if (title != null) {
            h3().text(title).end();
        }
        table().border("0");
        return this;
    }

    SimpleForm endSection() {
        end();
        return this;
    }

}
