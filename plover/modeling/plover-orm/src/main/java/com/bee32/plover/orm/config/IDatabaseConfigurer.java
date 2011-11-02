package com.bee32.plover.orm.config;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.arch.util.IOrdered;
import com.bee32.plover.orm.dao.CommonDataManager;

public interface IDatabaseConfigurer
        extends IOrdered {

    void configureHibernateProperties(Properties properties)
            throws Exception;

    void configureSessionFactoryBean(LocalSessionFactoryBean bean)
            throws Exception;

    void commonDataInit(ApplicationContext appctx, CommonDataManager dataManager)
            throws Exception;

    void commonDataUninit(ApplicationContext appctx, CommonDataManager dataManager)
            throws Exception;

}
