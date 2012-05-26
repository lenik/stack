package com.bee32.sem.process;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

public class SEMProcessPageDirectoryTest
        extends Assert {

    {
        SEMProcessModule module = new SEMProcessModule();
        module.preamble();
    }

    @Test
    public void testGetListPage() {
        IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(VerifyPolicyPref.class);
        Location listLoc = pageDir.getPagesForView(StandardViews.LIST).get(0);
        String listBase = listLoc.getBase();
        // System.out.println(listLoc);
        assertEquals("/3/15/2/1/pref/index.do", listBase);
    }

    @Test
    public void testWhichClass1() {
        Class<?> c1 = PageDirectory.whichClass("/3/15/2/1/pref/index-rich.jsf");
        assertEquals(VerifyPolicyPref.class, c1);
    }

    @Test
    public void testWhichClass2() {
        Class<?> c2 = PageDirectory.whichClass("/3/15/2/1/pref/");
        assertEquals(VerifyPolicyPref.class, c2);
    }

    @Test
    public void testWhichClassNoSep() {
        Class<?> c3 = PageDirectory.whichClass("/3/15/2/1/pref");
        assertNull(c3);
    }

    @Test
    public void testWhichClassNoEntry() {
        Class<?> c3 = PageDirectory.whichClass("/3/15/2/1/other");
        assertNull(c3);
    }

}
