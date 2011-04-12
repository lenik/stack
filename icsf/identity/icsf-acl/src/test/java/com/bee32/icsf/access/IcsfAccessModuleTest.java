package com.bee32.icsf.access;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.restful.test.RESTfulTestCase;

@Using(IcsfIdentityUnit.class)
public class IcsfAccessModuleTest
        extends RESTfulTestCase {

    public static void main(String[] args)
            throws Exception {

        new IcsfAccessModuleTest().browseAndWait(IcsfAccessModule.class);

    }

}
