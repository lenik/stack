package com.bee32.plover.faces.test;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.faces.view.ViewBean;

public class LeftValueBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    SimpleEntry entry = new SimpleEntry();
    List<SimpleEntry> entries = new ArrayList<SimpleEntry>();

    public LeftValueBean() {
        entries.add(new SimpleEntry("1", "A"));
        entries.add(new SimpleEntry("2", "B"));
        entries.add(new SimpleEntry("3", "C"));
    }

    public Object echo(Object obj) {
        return obj;
    }

    public SimpleEntry getEntry() {
        return entry;
    }

    public void setEntry(SimpleEntry entry) {
        this.entry = entry;
    }

    public List<SimpleEntry> getEntries() {
        return entries;
    }

}
