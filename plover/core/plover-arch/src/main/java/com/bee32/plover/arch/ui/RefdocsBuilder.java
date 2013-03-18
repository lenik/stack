package com.bee32.plover.arch.ui;

import java.util.*;

/**
 * 构建型帮助索引，支持帮助条目的更改，用于中间变量的目的。
 */
public class RefdocsBuilder
        implements IRefdocs {

    private Map<String, List<IRefdocEntry>> tagmap;

    public RefdocsBuilder() {
        tagmap = new TreeMap<String, List<IRefdocEntry>>();
    }

    public Set<String> getTags() {
        return tagmap.keySet();
    }

    @Override
    public Collection<? extends IRefdocEntry> getEntries(String tag) {
        List<IRefdocEntry> entries = tagmap.get(tag);
        if (entries == null)
            return Collections.<IRefdocEntry> emptyList();
        return Collections.unmodifiableCollection(entries);
    }

    @Override
    public IRefdocEntry getDefaultEntry(String tag) {
        List<IRefdocEntry> entries = tagmap.get(tag);
        if (entries == null || entries.isEmpty())
            return null;
        return entries.get(0);
    }

    public void add(IRefdocEntry entry) {
        add(entry, entry.getTags());
    }

    public void add(IRefdocEntry entry, String... tags) {
        if (entry == null)
            throw new NullPointerException("entry");
        if (tags == null)
            throw new NullPointerException("tags");
        if (tags.length == 0)
            throw new IllegalArgumentException("No tag specified");
        for (String tag : tags) {
            List<IRefdocEntry> entries = tagmap.get(tag);
            if (entries == null) {
                entries = new ArrayList<IRefdocEntry>(1);
                tagmap.put(tag, entries);
            }
            entries.add(entry);
        }
    }

}
