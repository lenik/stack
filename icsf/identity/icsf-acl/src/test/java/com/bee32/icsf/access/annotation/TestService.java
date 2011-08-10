package com.bee32.icsf.access.annotation;

import com.bee32.plover.arch.DataService;

public class TestService
        extends DataService {

    @AccessCheck
    public void method1() {
    }

    @AccessCheck(name = "foo")
    public void method2() {
    }

}
