package com.bee32.plover.inject.spring;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.test.WiredTestCase;

public class DefaultScopeTest
        extends WiredTestCase {

    @ComponentTemplate
    // @Prototype
    static class Dog {
    }

    @Inject
    ApplicationContext appctx;

    @Test
    public void testGetBean1() {
        Dog dog1 = appctx.getBean(Dog.class);
        Dog dog2 = appctx.getBean(Dog.class);
        System.out.println(dog1);
        System.out.println(dog2);
    }

}
