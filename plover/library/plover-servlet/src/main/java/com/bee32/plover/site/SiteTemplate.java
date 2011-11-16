package com.bee32.plover.site;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.ClassResource;
import javax.free.Doc;
import javax.free.URLResource;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;
import com.bee32.plover.servlet.util.ThreadHttpContext;
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
        for (Entry<String, IPageGenerator> entry : SiteManagerServlet.pages.map.entrySet()) {
            IPageGenerator pageGenerator = entry.getValue();
            if (!(pageGenerator instanceof InstantiatePageGenerator))
                continue;

            InstantiatePageGenerator ipg = (InstantiatePageGenerator) pageGenerator;
            Class<?> pageClass = ipg.pageClass;

            int modifiers = pageClass.getModifiers();
            if (!Modifier.isPublic(modifiers))
                continue;

            Doc _doc = pageClass.getAnnotation(Doc.class);

            String href = entry.getKey();
            String title = pageClass.getSimpleName();
            if (_doc != null)
                title = _doc.value();

            li().a().href(href).text(title).end(2);
        }

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

    protected final HttpServletRequest request;

    {
        request = ThreadHttpContext.getRequest();
        html();
        head();
        {
            // meta().httpEquiv("content-type").content("text/html; encoding=utf-8");
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
                boolean hidden = false;

                while (true) {
                    if (name.startsWith("-"))
                        readOnly = true;
                    else if (name.startsWith("!"))
                        critical = true;
                    else if (name.startsWith("."))
                        hidden = true;
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

                if (hidden) {
                    tr().td().colspan("3");
                    String sval = value == null ? "" : value.toString();
                    input().name(name).type("hidden").value(sval).end();
                    end(2); // .tr.td
                    continue;
                }

                tr();
                th().classAttr("key" + (critical ? " critical" : "")).text(label).end();

                td().classAttr("value");

                Html input;

                if (value == null || value instanceof String || value instanceof Number) {
                    input = input().name(name).type("text");
                    String sval = value == null ? "" : value.toString();
                    input.value(sval);

                } else if (value instanceof Location) {
                    Location location = (Location) value;
                    String _location = Locations.qualify(location);
                    String href = location.resolveAbsolute(request);
                    span();
                    {
                        input = input().name(name).type("text");
                        String sval = value == null ? "" : _location;
                        input.value(sval);
                        end();

                        if (href != null)
                            img().classAttr("icon").src(href).end();
                    }

                } else if (value instanceof Boolean) {
                    input = input().name(name).type("checkbox").value("1");
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

    protected void simpleRow(String name, Object value) {
        String _value = value == null ? "" : value.toString();
        tr();
        th().classAttr("key").text(name).end();
        td().classAttr("value").text(_value).end();
        end();
    }

    protected void simpleRowImage(String name, Location image, Location href) {
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String _image = image.resolveAbsolute(request);

        tr();
        th().classAttr("key").text(name).end();
        td().classAttr("value");
        if (href != null) {
            String _href = href.resolveAbsolute(request);
            a().href(_href);
        }
        img().classAttr("icon").src(_image).end();
        if (href != null)
            end();
        end(2);
    }

    protected void simpleRow(String name, Object value, String comment) {
        String _value = value == null ? "" : value.toString();
        tr();
        th().classAttr("key").text(name).end();
        td().classAttr("value").text(_value).end();
        td().classAttr("comment").text(comment == null ? "" : comment).end();
        end();
    }

}
