package com.seccaproject.plover.arch.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class HelpIndex {

    private Map<String, HelpEntry[]> tagmap;

    public HelpIndex(TreeSet<HelpEntry> entries) {
        tagmap = new HashMap<String, HelpEntry[]>();
    }

    public void add(HelpEntry entry) {
        tagmap.add(entry);
    }

    /**
     * @return <code>null</code> if no help available for the specified <code>tag</code>.
     */
    public URL get(String tag) {

    }
}
