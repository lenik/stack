package com.bee32.plover.orm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.util.hibernate.SessionFactoryBuilder;
import com.bee32.plover.orm.util.hibernate.TestSessionFactoryBuilder;

@Configuration
public class PloverOrmTestModule
        extends Module {

    @Override
    protected void preamble() {
    }

    @Bean
    public SessionFactoryBuilder getTestSessionFactoryBuilder() {
        return TestSessionFactoryBuilder.getInstance();
    }

}
