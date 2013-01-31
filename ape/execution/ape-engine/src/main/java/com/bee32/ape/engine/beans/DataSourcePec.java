package com.bee32.ape.engine.beans;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import com.bee32.ape.engine.base.IAppCtxAccess.ctx;
import com.bee32.plover.thirdparty.hibernate.util.IHibernatePropertyNames;

public class DataSourcePec
        extends AbstractPec
        implements IHibernatePropertyNames {

    boolean inMemory = false;

    @Override
    public void configure(ProcessEngineConfigurationImpl configuration) {
        if (inMemory) {
            configuration.setJdbcUrl("jdbc:h2:mem:activiti");
            configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);
        }

        // site = ThreadHttpContext.getSiteInstance();
        // SessionFactory sessionFactory = ctx.bean.getBean(SessionFactory.class);

        DataSource dataSource = ctx.bean.getBean(DataSource.class);
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate("true");
    }

    @Override
    protected void configureSpringSpecific(SpringProcessEngineConfiguration configuration) {
        super.configureSpringSpecific(configuration);

        HibernateTransactionManager transactionManager = appctx.getBean(HibernateTransactionManager.class);
        configuration.setTransactionManager(transactionManager);
    }

}
