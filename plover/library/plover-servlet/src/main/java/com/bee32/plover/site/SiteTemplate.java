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
        return "应用站点管理 (V-0.3)";
    }

    protected String getSubTitle() {
        return classDoc;
    }

    protected Html _menu() {
        ul();
        li().a().href("index").text("所有站点").end(2);
        li().a().href("create").text("新建站点").end(2);
        li().a().href("data").text("数据备份").end(2);
        li().a().href("cache").text("缓存管理").end(2);
        li().a().href("status").text("运行状态").end(2);
        li().a().href("helpDoc").text("帮助信息").end(2);

        end();
        return this;
    }

    protected void _content() {
        text("No content.");
    }

    protected void _footer() {
        text("(C) 版权所有 智恒软件有限公司 2010-2011");
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

    /**
     * array: each entry as { name, label, value } label may be in format "LABEL:HELPDOC", where
     * HELPDOC is shown as control title.
     */
    protected void simpleForm(String action, Object... array) {
        form().method("get").action(action);
        {
            table().border("0");
            for (int i = 0; i < array.length; i += 3) {
                String name = (String) array[i];
                String label = (String) array[i + 1];
                Object value = array[i + 2];
                String tooltip = "";
                int labelColon = label.indexOf(':');
                if (labelColon != -1) {
                    tooltip = label.substring(labelColon + 1);
                    label = label.substring(0, labelColon);
                }

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
                    String sval = value == null ? "" : value.toString();
                    input.value(sval).end();
                }
                end(); // .td
                td().text(tooltip).end();
                end(); // .tr
            }
            tr().td().colspan("3").align("left");
            input().type("submit").name("save").value("保存").end();
            text("");
            input().type("reset").value("重置").end();
            end(3); // .table.tr.td
        }
        end();
    }

}
