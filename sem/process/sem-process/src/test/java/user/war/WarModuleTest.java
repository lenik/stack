package user.war;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(WarUnit.class)
public class WarModuleTest
        extends SEMTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    @Override
    protected String getLoggedInUser() {
        return "eva";
    }

    public static void main(String[] args)
            throws IOException {
        new WarModuleTest().browseAndWait(//
                // SEMProcessModule.class//
                // AllowListController.PREFIX + "index.do"//
                // MultiLevelController.PREFIX + "index.do"//
                // VerifyPolicyPrefController.PREFIX + "index.do"//
                WarModule.PREFIX + "/attack/index-rich.jsf");
    }

}
