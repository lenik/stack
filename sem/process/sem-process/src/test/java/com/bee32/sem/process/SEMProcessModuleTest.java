package com.bee32.sem.process;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPrefController;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMProcessTestUnit.class)
public class SEMProcessModuleTest
        extends SEMTestCase {

    public static void main(String[] args)
            throws IOException {

        new SEMProcessModuleTest().browseAndWait(//
                // SEMProcessModule.class//
                // AllowListController.PREFIX + "index.htm"//
                // MultiLevelController.PREFIX + "index.htm"//
                VerifyPolicyPrefController.PREFIX + "index.htm"//
                );
    }

}
