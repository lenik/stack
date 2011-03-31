package com.bee32.plover.orm.config.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.qualifier.TestPurpose;

@Component
@TestPurpose
@Lazy
public class DefaultTestSessionFactoryBean
        extends TestPurposeSessionFactoryBean {

    public DefaultTestSessionFactoryBean() {
        super();
    }

}
