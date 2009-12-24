package com.seccaproject.plover.arch.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 构建型帮助索引，支持帮助条目的更改，用于中间变量的目的。
 *
 * @test {@link RefdocsBuilderTest}
 */
public class RefdocsBuilder
        implements IRefdocs {

    private Map<String, List<IRefdocEntry>> tagmap;

    public RefdocsBuilder() {
        tagmap = new TreeMap<String, List<IRefdocEntry>>();
    }

    public String[] getTags() {
        String[] tags = tagmap.keySet().toArray(new String[0]);
        return tags;
    }

    @Override
    public Collection<? extends IRefdocEntry> getEntries(String tag) {
        List<IRefdocEntry> entries = tagmap.get(tag);
        if (entries == null)
            return Collections.emptyList();
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
