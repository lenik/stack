package com.bee32.plover.test;

import org.springframework.context.annotation.Bean;

// @Configuration
public class FooConfig {

    @Bean
    FooBean foo() {
        return new FooBean();
    }

}
