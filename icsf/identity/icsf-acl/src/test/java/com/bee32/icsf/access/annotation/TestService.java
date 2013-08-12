package com.bee32.icsf.access.annotation;

import com.bee32.plover.arch.DataService;

public class TestService
        extends DataService {

    /**
     * First
     *
     * <p lang="zh-cn">
     * 方法1
     */
    @AccessCheck
    public void method1() {
    }

    /**
     * Foo Action
     *
     * <p lang="zh-cn">
     * Foo 动作
     */
    @AccessCheck(name = "foo")
    public void method2() {
    }

}
