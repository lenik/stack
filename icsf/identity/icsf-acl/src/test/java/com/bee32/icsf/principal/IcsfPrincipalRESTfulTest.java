package com.bee32.icsf.principal;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.restful.test.RESTfulTestCase;

@Using(IcsfPrincipalUnit.class)
public class IcsfPrincipalRESTfulTest
        extends RESTfulTestCase {

    public static void main(String[] args)
            throws IOException {
        new IcsfPrincipalRESTfulTest().browseAndWait(IcsfPrincipalModule.class);
    }

}
