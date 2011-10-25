package com.bee32.plover.site;

import java.io.IOException;

import javax.free.ClassResource;
import javax.free.DocUtil;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.googlecode.jatl.Html;

public class SiteTemplate
        extends HtmlBuilder {

    String classDoc;
    TextMap args;
    SiteInstance siteInstance;

    public SiteTemplate(TextMap args) {
        this.args = args;
        parse(args);
        try {
            classDoc = DocUtil.getClassDoc(getClass());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void parse(TextMap args) {
        siteInstance = (SiteInstance) args.get("siteInstance");
    }

    public SiteInstance getSiteInstance() {
        return siteInstance;
    }

    protected String getTitle() {
        return "Site Manager";
    }

    protected String getSubTitle() {
        return classDoc;
    }

    protected Html _menu() {
        ul();
        li().a().end();
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
            SITE_CSS = ClassResource.classData(SiteTemplate.class, "css").forRead().readTextContents();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    {
        html();
        head();
        {
            title(getTitle()).end();
            style().type("css").text(SITE_CSS).end();
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
            tr().td().colspan("2").align("center");
            input().name("submit").value("Submit").end();
            text("&nbsp;");
            input().name("reset").value("Reset").end();
            end(3); // .table.tr.td
        }
        end();
    }

}
