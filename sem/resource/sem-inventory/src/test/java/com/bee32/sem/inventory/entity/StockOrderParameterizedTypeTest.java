package com.bee32.sem.inventory.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;

public class StockOrderParameterizedTypeTest {

    @Test
    public void testGetCreateFormLocation() {
        StockOrder order1 = new StockOrder(null, StockOrderSubject.OSP_IN);
        IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(order1);
        Location location = pageDir.getPageForView(StandardViews.CREATE_FORM);
        // System.out.println(location.getBase());
        assertEquals("/3/15/3/2/outsourcingIn/createForm.do?subject=OSPI", location.getBase());
    }

}
