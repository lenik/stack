package com.bee32.plover.arch.ui.res;

import java.net.MalformedURLException;
import java.net.URL;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.ui.RefdocEntry;
import com.bee32.plover.arch.ui.SimpleRefdocs;
import com.bee32.plover.arch.util.res.ClassResourceResolver;
import com.bee32.plover.arch.util.res.IPropertyAcceptor;
import com.bee32.plover.arch.util.res.IResourceResolver;

public class InjectedRefdocs
        extends SimpleRefdocs
        implements IPropertyAcceptor {

    private IResourceResolver resourceResolver;

    public InjectedRefdocs(Class<?> baseClass) {
        this(new ClassResourceResolver(baseClass));
    }

    public InjectedRefdocs(IResourceResolver resourceResolver) {
        if (resourceResolver == null)
            throw new NullPointerException("resourceResolver");
        this.resourceResolver = resourceResolver;
    }

    static final int PROP_TITLE = 1;
    static final int PROP_TAGS = 2;
    static final int PROP_URL = 3;

    @Override
    public void receive(String key, String content) {
        int dot = key.lastIndexOf('.');
        if (dot != -1) {
            String suffix = key.substring(dot + 1);
            key = key.substring(0, dot);

            int propertyType = 0;
            if ("title".equals(suffix))
                propertyType = PROP_TITLE;
            else if ("tags".equals(suffix))
                propertyType = PROP_TAGS;
            else if ("url".equals(suffix))
                propertyType = PROP_URL;
            else
                return;

            RefdocEntry entry = refdocEntries.get(key);
            if (entry == null)
                refdocEntries.put(key, entry = new RefdocEntry());

            // Populate the entry.
            switch (propertyType) {
            case PROP_TITLE:
                entry.setTitle(content);
                break;

            case PROP_TAGS:
                String[] tagv = content.split("\\s+");
                entry.setTags(tagv);
                for (String tag : tagv)
                    allTags.add(tag);
                break;

            case PROP_URL:
                URL resourceUrl;
                if (content.contains("//")) // Absolute-URL
                    try {
                        resourceUrl = new URL(content);
                    } catch (MalformedURLException e) {
                        throw new IllegalUsageException(e.getMessage(), e);
                    }
                else { // Class-Relative URL
                    try {
                        resourceUrl = resourceResolver.resolve(content);
                    } catch (MalformedURLException e) {
                        throw new IllegalUsageException(e.getMessage(), e);
                    }
                    if (resourceUrl == null)
                        throw new IllegalUsageException("Class resource isn't existed: " + content);
                }
                entry.setURL(resourceUrl);
                break;
            }
        } // last-dot
    }

}
