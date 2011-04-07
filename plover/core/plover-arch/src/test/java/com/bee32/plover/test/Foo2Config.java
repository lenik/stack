package com.bee32.plover.test;

import org.springframework.context.annotation.Bean;

public class Foo2Config {

    @Bean
    FooBean foo() {
        return new FooBean();
    }

}
