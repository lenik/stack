package com.bee32.sem.process;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

public class SEMProcessMenuTest
        extends Assert {

    @Test
    public void testGetListPage() {
        SEMProcessModule module = new SEMProcessModule();
        module.preamble();
        IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(VerifyPolicyPref.class);
        Location listLoc = pageDir.getPageForView(StandardViews.LIST);
        String listBase = listLoc.getBase();
        // System.out.println(listLoc);
        assertEquals("/3/15/2/1/pref/index-rich.jsf", listBase);
    }

}
