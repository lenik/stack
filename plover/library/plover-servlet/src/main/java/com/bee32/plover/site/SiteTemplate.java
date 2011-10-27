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

    protected void _content()
            throws Exception {
        text("No content.");
    }

    protected void _footer() {
        text("(C) 版权所有 智恒软件有限公司 2010-2011");
    }

    static String SITE_CSS = getSiteCss();

    static String getSiteCss() {
        String siteCss;
        try {
            URLResource cssResource = ClassResource.classData(SiteTemplate.class, "css");
            siteCss = cssResource.forRead().readTextContents();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return siteCss;
    }

    {
        html();
        head();
        {
            title(getTitle()).end();
            style().type("text/css").text(getSiteCss()).end(); // OPT
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
                try {
                    _content();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
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
     * array: each entry as { name, label, value }
     *
     * action may be in format "href:action-text"
     *
     * name may be prefixed with `-` for read-only fields.
     *
     * label may be in format "LABEL:HELPDOC", where HELPDOC is shown as control title.
     */
    protected void simpleForm(String action, Object... array) {
        String actionText = "保存";
        int actionColon = action.indexOf(':');
        if (actionColon != -1) {
            actionText = action.substring(actionColon + 1);
            action = action.substring(0, actionColon);
        }

        form().method("get").action(action);
        {
            table().classAttr("tcf").border("0");
            for (int i = 0; i < array.length; i += 3) {
                String name = (String) array[i];
                boolean critical = false;
                boolean readOnly = false;

                while (true) {
                    if (name.startsWith("-"))
                        readOnly = true;
                    else if (name.startsWith("!"))
                        critical = true;
                    else
                        break;
                    name = name.substring(1);
                }

                String label = (String) array[i + 1];
                String tooltip = "";
                int labelColon = label.indexOf(':');
                if (labelColon != -1) {
                    tooltip = label.substring(labelColon + 1);
                    label = label.substring(0, labelColon);
                }

                Object value = array[i + 2];

                tr();
                th().classAttr("key" + (critical ? " critical" : "")).text(label).end();

                td().classAttr("value");

                Html input;

                if (value == null || value instanceof String || value instanceof Number) {
                    input = input().name(name).type("text");
                    String sval = value == null ? "" : value.toString();
                    input.value(sval);

                } else if (value instanceof Boolean) {
                    input = input().name(name).type("checkbox");
                    Boolean bval = (Boolean) value;
                    if (bval)
                        input.selected("selected");

                } else if (value instanceof Enum<?>) {
                    Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) value.getClass();
                    boolean hasLabel = ILabel.class.isAssignableFrom(enumClass);

                    Enum<?>[] candidates = EnumUtil.values(enumClass);

                    input = select().name(name);
                    for (Enum<?> candidate : candidates) {
                        boolean selected = value.equals(candidate);
                        Html option = option().value(candidate.name());
                        if (selected)
                            option.selected("selected");
                        if (hasLabel) {
                            String candidateLabel = ((ILabel) candidate).getLabel();
                            option.text(candidateLabel);
                        } else {
                            option.text(candidate.name());
                        }
                        option.end();
                    }
                } else {
                    throw new UnsupportedOperationException("Unsupported field value type for simple-form: " + value);
                }

                if (readOnly)
                    input.readonly("readonly");
                input.end();

                end(); // .td
                td().classAttr("comment").text(tooltip).end();
                end(); // .tr
            }
            tr().td().colspan("3").align("left");
            input().type("submit").name("save").value(actionText).end();
            text("");
            input().type("reset").value("重置").end();
            end(3); // .table.tr.td
        }
        end();
    }
}
