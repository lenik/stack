package com.bee32.plover.site;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.DocUtil;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public class SiteTemplateSupport
        extends HtmlBuilder {

    protected String classDoc;
    protected TextMap args;
    protected SiteManager siteManager;
    protected SiteInstance siteInstance;

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

        try {
            classDoc = DocUtil.getClassDoc(getClass());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void parse(TextMap args) {
        siteManager = (SiteManager) args.get("siteManager");
        siteInstance = (SiteInstance) args.get("siteInstance");
    }

    public SiteManager getSiteManager() {
        return siteManager;
    }

    public SiteInstance getSiteInstance() {
        return siteInstance;
    }

}
