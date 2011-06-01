package com.bee32.sem.opportunity;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.opportunity.SEMOpportunityUnit;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMOpportunityUnit.class)
public class SEMOpportunityModuleTest
        extends SEMTestCase {

    public SEMOpportunityModuleTest() {
        super();
    }

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected int getRefreshPeriod() {
        return 0;
    }

    @Override
    protected String getLoggedInUser() {
        return "admin";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMOpportunityModuleTest().browseAndWait("/customer/chance/index.htm");
    }

}
