package com.bee32.zebra.oa.calendar.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.bodz.bas.t.order.AbstractNonNullComparator;

import com.bee32.zebra.oa.calendar.LogEntry;

public class LogEntryGroup
        extends ArrayList<LogEntry> {

    private static final long serialVersionUID = 1L;

    private boolean bold;
    private boolean italics;
    private String inlineText;
    private List<String> icons;

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalics() {
        return italics;
    }

    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    public String getInlineText() {
        return inlineText;
    }

    public void setInlineText(String inlineText) {
        this.inlineText = inlineText;
    }

    public List<String> getIcons() {
        return icons;
    }

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    public void addIcon(Object icon) {
        if (icon == null)
            throw new NullPointerException("icon");
        if (icons == null)
            icons = new ArrayList<String>();
        icons.add(icon.toString());
    }

    public void sort() {
        Collections.sort(this, LogEntryComparator.INSTANCE);
    }

}

class LogEntryComparator
        extends AbstractNonNullComparator<LogEntry> {

    @Override
    public int compareNonNull(LogEntry o1, LogEntry o2) {
        Date date1 = o1.getDate();
        Date date2 = o2.getDate();
        return date1.compareTo(date2);
    }

    public static final LogEntryComparator INSTANCE = new LogEntryComparator();

}