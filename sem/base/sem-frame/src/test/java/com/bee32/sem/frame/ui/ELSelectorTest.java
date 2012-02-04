package com.bee32.sem.frame.ui;

import java.util.ArrayList;
import java.util.List;

import javax.free.IllegalUsageException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.faces.test.SimpleEntry;

public class ELSelectorTest
        extends Assert {

    static SimpleEntry a = new SimpleEntry("1", "A");
    static SimpleEntry b = new SimpleEntry("2", "B");
    static SimpleEntry c = new SimpleEntry("3", "C");

    static SimpleEntry entry = b;
    static List<SimpleEntry> entries = new ArrayList<SimpleEntry>();

    ELSelector selector;

    public ELSelectorTest() {
        entries.add(a);
        entries.add(b);
        entries.add(c);
        // selector = new ELSelector(entries, this, "${context.entry}", "it", "${it.key}",
// "${it.label}");
        selector = new ELSelector(entries, this, "${context.entry}", null, null, null);
    }

    @Before
    public void init() {
        entry = b;
    }

    @Test
    public void testGetCurrent() {
        assertEquals(b, selector.getValue());
    }

    @Test
    public void testSelect() {
        selector.setValueId("1");
        assertEquals(a, entry);
        selector.setValueId("3");
        assertEquals(c, entry);
    }

    @Test
    public void testSelectNull() {
        selector.setValueId(selector.getNullId());
        assertNull(null);
    }

    @Test(expected = IllegalUsageException.class)
    public void testSelectInvalid() {
        selector.setValueId("InVaLiD");
    }

    public SimpleEntry getEntry() {
        return entry;
    }

    public void setEntry(SimpleEntry _entry) {
        entry = _entry;
    }

}
