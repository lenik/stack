package com.bee32.plover.orm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bee32.plover.orm.util.hibernate.SessionFactoryBuilder;
import com.bee32.plover.orm.util.hibernate.TestSessionFactoryBuilder;

@Configuration
public class OrmConfiguration {

    @Bean
    public SessionFactoryBuilder getTestSessionFactoryBuilder() {
        return TestSessionFactoryBuilder.getInstance();
    }

}
