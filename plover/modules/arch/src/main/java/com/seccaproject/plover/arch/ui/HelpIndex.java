package com.seccaproject.plover.arch.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @test {@link HelpIndexTest}
 */
public class HelpIndex
        implements IHelpIndex {

    private Map<String, List<IHelpEntry>> tagmap;

    public HelpIndex() {
        tagmap = new TreeMap<String, List<IHelpEntry>>();
    }

    public String[] getTags() {
        String[] tags = tagmap.keySet().toArray(new String[0]);
        return tags;
    }

    @Override
    public Collection<? extends IHelpEntry> getEntries(String tag) {
        List<IHelpEntry> entries = tagmap.get(tag);
        if (entries == null)
            return Collections.emptyList();
        return Collections.unmodifiableCollection(entries);
    }

    @Override
    public IHelpEntry getPreferredEntry(String tag) {
        List<IHelpEntry> entries = tagmap.get(tag);
        if (entries == null || entries.isEmpty())
            return null;
        return entries.get(0);
    }

    public void add(IHelpEntry entry) {
        add(entry, entry.getTags());
    }

    public void add(IHelpEntry entry, String... tags) {
        if (entry == null)
            throw new NullPointerException("entry");
        if (tags == null)
            throw new NullPointerException("tags");
        if (tags.length == 0)
            throw new IllegalArgumentException("No tag specified");
        for (String tag : tags) {
            List<IHelpEntry> entries = tagmap.get(tag);
            if (entries == null) {
                entries = new ArrayList<IHelpEntry>(1);
                tagmap.put(tag, entries);
            }
            entries.add(entry);
        }
    }

}
