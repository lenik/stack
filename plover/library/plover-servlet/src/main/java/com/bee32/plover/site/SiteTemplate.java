package com.bee32.plover.site;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.Doc;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.html.InstantiatePageGenerator;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.googlecode.jatl.Html;

public class SiteTemplate
        extends SiteTemplateSupport {

    protected HttpServletRequest request = ThreadHttpContext.getRequest();

    public SiteTemplate() {
        super();
    }

    public SiteTemplate(Map<String, ?> _args) {
        super(_args);
    }

    @Override
    public String getTitle() {
        return "应用站点管理 (V-0.8.1)";
    }

    @Override
    public String getCopyright() {
        return "(C) 版权所有 智恒软件有限公司 2010-2011";
    }

    protected String getSubTitle() {
        return classDoc;
    }

    @Override
    protected void _pageLayout() {
        div().classAttr("header");
        _pageHeader();
        end();

        div().classAttr("navigation");
        _menu();
        end();

        div().classAttr("content-container");
        {
            div().classAttr("menu");
            _menu();
            end();
            div().classAttr("content");
            _pageContent();
            end();
        }
        end();

        div().classAttr("footer");
        _pageFooter();
        end();
    }

    @Override
    protected void _pageContent() {
        h2().text(getSubTitle()).end();
        try {
            _content();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void _content()
            throws Exception {
        p().text("No content").end();
    }

    protected Html _menu() {
        ul();
        for (Entry<String, IPageGenerator> entry : SiteManagerServlet._pages.getPageMap().entrySet()) {
            IPageGenerator pageGenerator = entry.getValue();
            if (!(pageGenerator instanceof InstantiatePageGenerator))
                continue;

            InstantiatePageGenerator ipg = (InstantiatePageGenerator) pageGenerator;
            Class<?> pageClass = ipg.getPageClass();

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

}
