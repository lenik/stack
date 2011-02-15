package com.bee32.plover.test;

import org.junit.Test;

import com.bee32.plover.restful.DispatchFilter;

public class DispServiceTestCaseTest
        extends DispServiceTestCase {

    /**
     * @see DispatchFilter
     */
    @Test
    public void testCoreInfoCreditText()
            throws Exception {
        String credit = get("/3/12/2/1/credit.txt").getContent();
        System.out.println(credit);
    }

}
