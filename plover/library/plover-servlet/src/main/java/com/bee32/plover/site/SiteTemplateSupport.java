package com.bee32.plover.site;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.Doc;

import com.bee32.plover.arch.util.TextMap;

public class SiteTemplateSupport
        extends HtmlBuilder {

    protected String classDoc;
    protected TextMap args;
    protected SiteManager manager;
    protected SiteInstance site;

    public SiteTemplateSupport() {
        this(new HashMap<String, Object>());
    }

    public SiteTemplateSupport(Map<String, ?> _args) {
        args = new TextMap(_args);
        parse(args);

        // All params go into binding.
        for (Entry<String, ?> entry : _args.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            bind(name, value);
        }

        Doc _doc = getClass().getAnnotation(Doc.class);
        classDoc = _doc == null ? getClass().getSimpleName() : _doc.value();
    }

    protected void parse(TextMap args) {
        manager = SiteManager.getInstance();

        String siteName = (String) args.get("site");
        if (siteName != null)
            site = manager.getSite(siteName);
    }

}
