package com.bee32.plover.orm.wac;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.DisplayName;
import javax.free.Doc;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.site.SimpleServlet;

public class TypeAbbrDumpServlet
        extends SimpleServlet
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    public TypeAbbrDumpServlet() {
        pages.add("index", Index.class);
    }

    @DisplayName("Type Abbreviations")
    @Doc("Type Abbreviations")
    public static class Index
            extends HtmlTemplate {
        @Override
        protected void _pageContent() {
            TreeMap<String, String> map = new TreeMap<>(ABBR.getAbbrMap());
            table();
            {
                tr();
                {
                    th().text("ABBR").end();
                    th().text("FQCN").end();
                    end();
                }
                for (Entry<String, String> entry : map.entrySet()) {
                    String abbr = entry.getKey();
                    String fqcn = entry.getValue();
                    tr();
                    {
                        td().text(abbr).end();
                        td().text(fqcn).end();
                        end();
                    }
                }
                end();
            }
        }
    }

}
