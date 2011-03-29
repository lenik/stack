package com.bee32.icsf.access.annotation;

public class TestService {

    @Restricted
    public void method1() {
    }

    @Restricted(name = "foo")
    public void method2() {
    }

}
