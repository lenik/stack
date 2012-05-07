package com.bee32.plover.servlet.wac;

import java.util.Map.Entry;

import javax.free.DisplayName;
import javax.free.Doc;
import javax.free.StringArray;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.html.PageDefMap;
import com.bee32.plover.site.IPageGenerator;
import com.bee32.plover.site.SimpleServlet;

public class WacToolsServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public WacToolsServlet() {
        pages.add("servlets", Servlets.class);
    }

    @DisplayName("Servlets")
    @Doc("Dump configurerd servlets")
    public static class Servlets
            extends HtmlTemplate {

        @Override
        protected void _pageContent() {
            ServletContext servletContext = getRequest().getServletContext();
            // RabbitServer server = RabbitServer.getInstanceFromContext(servletContext);

            table().border("1").cellspacing("0").cellpadding("2");
            thead();
            tr();
            th().text("Registration").end();
            th().text("Mappings").end();
            th().text("Servlet-FQCN").end();
            th().text("Extras").end();

            int index = 0;
            for (Entry<String, ? extends ServletRegistration> ent : servletContext.getServletRegistrations().entrySet()) {
                // String key = ent.getKey();
                ServletRegistration reg = ent.getValue();
                String mappings = StringArray.join("; ", reg.getMappings());
                tr();

                if (++index % 2 == 0) {
                    style("background: #ffffcc");
                } else {
                    style("background: #ffffdd");
                }

                // cell(key);
                cell(reg.getName());
                cell(mappings);
                cell(reg.getClassName());

                td();
                {
                    try {
                        Class<?> servletClass = Class.forName(reg.getClassName());
                        if (SimpleServlet.class.isAssignableFrom(servletClass)) {
                            SimpleServlet simpleServlet = (SimpleServlet) servletClass.newInstance();
                            PageDefMap pageDefMap = simpleServlet.getPages();
                            for (Entry<String, IPageGenerator> pageEnt : pageDefMap.getPageMap().entrySet()) {
                                String pageKey = pageEnt.getKey();
                                // IPageGenerator pageGen = pageEnt.getValue();
                                line("Page: " + pageKey + "\n");
                            }
                        }
                    } catch (ReflectiveOperationException e) {
                        line("Error when get pages: " + e.getMessage() + "\n");
                    }

                    if (!reg.getInitParameters().isEmpty()) {
                        for (Entry<String, String> initparam : reg.getInitParameters().entrySet()) {
                            String initkey = initparam.getKey();
                            String initval = initparam.getValue();
                            line("Init-Param: " + initkey + " = " + initval + "\n");
                        }
                    }
                    end();
                } // td Extras
                end(); // tr
            } // for reg
            end();
        }

        void cell(Object content) {
            String str = String.valueOf(content);
            td().text(str).end();
        }

        void line(Object content) {
            String str = String.valueOf(content);
            text(str);
            br().end();
        }

    }

}
