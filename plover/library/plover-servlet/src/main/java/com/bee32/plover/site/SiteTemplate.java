package com.bee32.plover.site;

import java.io.IOException;
import java.util.Map;

import javax.free.ClassResource;
import javax.free.URLResource;

import com.googlecode.jatl.Html;

public class SiteTemplate
        extends SiteTemplateSupport {

    public SiteTemplate() {
        super();
    }

    public SiteTemplate(Map<String, ?> _args) {
        super(_args);
    }

    protected String getTitle() {
        return "Site Manager";
    }

    protected String getSubTitle() {
        return classDoc;
    }

    protected Html _menu() {
        ul();
        li().a().end().end();
        end();
        return this;
    }

    protected void _content() {
        text("No content.");
    }

    protected void _footer() {
        text("Copyright 2010");
    }

    static String SITE_CSS;
    static {
        try {
            URLResource cssResource = ClassResource.classData(SiteTemplate.class, "css");
            SITE_CSS = cssResource.forRead().readTextContents();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    {
        html();
        head();
        {
            title(getTitle()).end();
            style().type("text/css").text(SITE_CSS).end();
            end();
        }

        body();
        div().classAttr("container");
        {
            div().classAttr("header").h1().text(getTitle()).end().end();

            div().classAttr("navigation");
            _menu();
            end();

            div().classAttr("content-container");
            {
                div().classAttr("menu");
                _menu();
                end();

                div().classAttr("content");
                h2().text(getSubTitle()).end();
                _content();
                end();
            }
            end();

            div().classAttr("footer");
            _footer();
            end();
        }

        endAll();
    }

    // html sugars

    protected void simpleForm(String action, Object... array) {
        form().method("get").action(action);
        {
            table().border("0");
            for (int i = 0; i < array.length; i += 3) {
                String name = (String) array[i];
                String label = (String) array[i + 1];
                Object value = array[i + 2];
                tr();
                th().text(label).end();
                td();
                if (value instanceof Boolean) {
                    Html input = input().name(name).type("check");
                    Boolean bval = (Boolean) value;
                    if (bval)
                        input.selected("selected");
                } else {
                    Html input = input().name(name).type("text");
                    String sval = String.valueOf(value);
                    input.value(sval).end();
                }
                end(2); // .tr.td
            }
            tr().td().colspan("2").align("left");
            input().type("submit").name("submit").value("Save").end();
            text("");
            input().type("reset").value("Reset").end();
            end(3); // .table.tr.td
        }
        end();
    }

}
