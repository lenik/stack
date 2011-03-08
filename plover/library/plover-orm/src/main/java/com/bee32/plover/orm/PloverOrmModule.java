package com.bee32.plover.orm;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnits;
import com.bee32.plover.orm.util.hibernate.SessionFactoryBuilder;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Configuration
@Oid({ 3, 12, PloverOids.library, PloverOids.libraryORM })
public class PloverOrmModule
        extends Module {

    @Override
    protected void preamble() {
    }

    @Inject
    private SessionFactoryBuilder builder;

    @Bean
    public SessionFactory getGlobalSessionFactory() {
        PersistenceUnit globalUnit = PersistenceUnits.getInstance();
        SessionFactory sessionFactory = builder.buildForUnits(globalUnit);
        return sessionFactory;
    }

}
