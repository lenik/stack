package com.bee32.sem.process.ape;

import javax.sql.DataSource;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.site.scope.PerSite;
import com.bee32.plover.thirdparty.hibernate.util.IHibernatePropertyNames;

@PerSite
@Lazy
public class ApeSiteProcessEngineConfiguration
        extends StandaloneProcessEngineConfiguration
        implements IHibernatePropertyNames {

    static class ctx
            extends DefaultDataAssembledContext {
    }

    boolean inMemory = false;

    public ApeSiteProcessEngineConfiguration() {
        if (inMemory) {
            setJdbcUrl("jdbc:h2:mem:activiti");
            setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_CREATE_DROP);
        }

        // site = ThreadHttpContext.getSiteInstance();
        // SessionFactory sessionFactory = ctx.bean.getBean(SessionFactory.class);
        DataSource dataSource = ctx.bean.getBean(DataSource.class);
        setDataSource(dataSource);
        // setTransactionManager();
        setDatabaseSchemaUpdate("true");
        setJobExecutorActivate(true);
    }

}
