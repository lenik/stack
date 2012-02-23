package com.bee32.plover.orm.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.servlet.util.ServiceTemplateRC;

public abstract class AbstractDatabaseConfigurer
        extends ServiceTemplateRC
        implements IDatabaseConfigurer {

    static Logger logger = LoggerFactory.getLogger(AbstractDatabaseConfigurer.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureHibernateProperties(Properties properties)
            throws Exception {
    }

    @Override
    public void configureSessionFactoryBean(LocalSessionFactoryBean bean)
            throws Exception {
    }

    @Override
    public void commonDataInit(ApplicationContext appctx, CommonDataManager dataManager)
            throws Exception {
    }

    @Override
    public void commonDataUninit(ApplicationContext appctx, CommonDataManager dataManager)
            throws Exception {
    }

}
