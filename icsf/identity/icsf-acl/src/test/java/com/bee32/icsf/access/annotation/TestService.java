package com.bee32.icsf.access.annotation;

import com.bee32.plover.arch.EnterpriseService;

public class TestService
        extends EnterpriseService {

    @AccessCheck
    public void method1() {
    }

    @AccessCheck(name = "foo")
    public void method2() {
    }

}
