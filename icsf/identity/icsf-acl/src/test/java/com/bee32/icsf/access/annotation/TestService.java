package com.bee32.icsf.access.annotation;

public class TestService {

    @Checkpoint
    public void method1() {
    }

    @Checkpoint(name = "foo")
    public void method2() {
    }

}
