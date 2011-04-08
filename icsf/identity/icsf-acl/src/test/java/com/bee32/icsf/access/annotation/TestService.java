package com.bee32.icsf.access.annotation;


public class TestService {

    @AccessCheck
    public void method1() {
    }

    @AccessCheck(name = "foo")
    public void method2() {
    }

}
