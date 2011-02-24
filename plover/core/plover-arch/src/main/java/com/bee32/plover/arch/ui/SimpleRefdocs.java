package com.bee32.plover.arch.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SimpleRefdocs
        implements IRefdocs {

    /**
     * Refdoc-Qualifier -> Refdoc-Entry
     */
    protected final Map<String, RefdocEntry> refdocEntries;
    protected final Set<String> allTags;

    public SimpleRefdocs() {
        refdocEntries = new TreeMap<String, RefdocEntry>();
        allTags = new HashSet<String>();
    }

    @Override
    public Set<String> getTags() {
        return allTags;
    }

    /**
     * TODO - use prefetched-iterator in future.
     */
    @Override
    public Iterable<? extends IRefdocEntry> getEntries(String tag) {
        List<IRefdocEntry> list = new ArrayList<IRefdocEntry>();
        for (RefdocEntry entry : refdocEntries.values())
            if (entry.hasTag(tag)) {
                list.add(entry);
                continue;
            }
        return list;
    }

    @Override
    public IRefdocEntry getDefaultEntry(String tag) {
        for (RefdocEntry entry : refdocEntries.values())
            if (entry.hasTag(tag))
                return entry;
        return null;
    }

    public void addRefdoc(String qualifier, RefdocEntry refdocEntry) {
        refdocEntries.put(qualifier, refdocEntry);
    }

}
