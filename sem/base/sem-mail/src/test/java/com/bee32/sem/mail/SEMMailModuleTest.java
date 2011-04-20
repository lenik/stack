package com.bee32.sem.mail;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.mail.web.MailFilterController;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMMailUnit.class)
public class SEMMailModuleTest
        extends SEMTestCase {

    public static void main(String[] args)
            throws IOException {

        new SEMMailModuleTest().browseAndWait(//
                MailFilterController.PREFIX + "index.htm"//
                );
    }

}
