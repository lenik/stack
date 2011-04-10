package com.bee32.icsf.principal;

import java.io.IOException;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.restful.test.RestfulTestCase;

@Using(IcsfIdentityUnit.class)
public class IcsfPrincipalModuleTest
        extends RestfulTestCase {

    public static void main(String[] args)
            throws IOException {
        new IcsfPrincipalModuleTest().browseAndWait(IcsfPrincipalModule.class);
    }

}
