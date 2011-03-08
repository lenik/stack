package com.bee32.plover.orm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bee32.plover.arch.Module;
import com.bee32.plover.inject.qualifier.RunConfig;
import com.bee32.plover.orm.util.hibernate.SessionFactoryForUnit;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Configuration
@Oid({ 3, 12, PloverOids.library, PloverOids.libraryORM })
public class PloverOrmModule
        extends Module {

    @Override
    protected void preamble() {
    }

    @Bean
    @RunConfig("default")
    public Object defaultSessionFactory() {
        return new SessionFactoryForUnit();
    }

}
