package com.bee32.sem.process;

import java.io.IOException;

import org.junit.Test;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.process.verify.builtin.web.MultiLevelController;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMProcessUnit.class)
public class SEMProcessModuleTest
        extends SEMTestCase {

    @Test
    public void testUser()
            throws Exception {
        String loc = "http://localhost:" + stl.getPort() + PREFIX + "/";
        System.out.println(loc);
    }

    @Override
    protected boolean isDebugMode() {
        return false;
    }

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMProcessModuleTest().browseAndWait(//
                // SEMProcessModule.class//
                // AllowListController.PREFIX + "index.htm"//
                MultiLevelController.PREFIX + "index.htm"//
                );
    }

}
