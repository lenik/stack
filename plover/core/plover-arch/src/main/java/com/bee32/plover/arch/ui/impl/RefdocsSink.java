package com.bee32.plover.arch.ui.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.i18n.nls.IPropertySink;
import com.bee32.plover.arch.ui.IRefdocEntry;
import com.bee32.plover.arch.ui.IRefdocs;
import com.bee32.plover.arch.ui.RefdocEntry;

public class RefdocsSink
        implements IRefdocs, IPropertySink {

    private Class<?> resourceBase;
    private Map<String, RefdocEntry> map;
    private Set<String> tags;

    public RefdocsSink(Class<?> resourceBase) {
        if (resourceBase == null)
            throw new NullPointerException("resourceBase");
        this.resourceBase = resourceBase;
        this.map = new HashMap<String, RefdocEntry>();
        this.tags = new HashSet<String>();
    }

    @Override
    public Set<String> getTags() {
        return tags;
    }

    /**
     * TODO - use prefetched-iterator in future.
     */
    @Override
    public Iterable<? extends IRefdocEntry> getEntries(String tag) {
        List<IRefdocEntry> list = new ArrayList<IRefdocEntry>();
        for (RefdocEntry entry : map.values())
            if (entry.hasTag(tag)) {
                list.add(entry);
                continue;
            }
        return list;
    }

    @Override
    public IRefdocEntry getDefaultEntry(String tag) {
        for (RefdocEntry entry : map.values())
            if (entry.hasTag(tag))
                return entry;
        return null;
    }

    @Override
    public void receive(String key, String content) {
        int dot = key.lastIndexOf('.');
        if (dot != -1) {
            String suffix = key.substring(dot + 1);
            key = key.substring(0, dot);
            char prop = 0;
            if ("title".equals(suffix))
                prop = 'T';
            else if ("tags".equals(suffix))
                prop = 'G';
            else if ("url".equals(suffix))
                prop = 'U';
            else
                return;
            RefdocEntry entry = map.get(key);
            if (entry == null)
                map.put(key, entry = new RefdocEntry());
            switch (prop) {
            case 'T':
                entry.setTitle(content);
                break;
            case 'G':
                String[] tagv = content.split("\\s+");
                entry.setTags(tagv);
                for (String tag : tagv)
                    tags.add(tag);
                break;
            case 'U':
                URL url;
                if (content.contains("//"))
                    try {
                        url = new URL(content);
                    } catch (MalformedURLException e) {
                        throw new IllegalUsageException(e.getMessage(), e);
                    }
                else {
                    url = resourceBase.getResource(content);
                    if (url == null)
                        throw new IllegalUsageException("Class resource isn't existed: " + content);
                }
                entry.setURL(url);
                break;
            }
        }
    }

}
