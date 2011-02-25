package com.bee32.plover.orm.unit;

import javax.sql.DataSource;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

class CT {

    {
        LocalSessionFactoryBean sb = new LocalSessionFactoryBean();

        DataSource source = null;
        sb.setDataSource(source); // BasicDataSource
        sb.setHibernateProperties(null); // hibernate.format_sql -> ...

        sb.setMappingResources(null); // List: classpath:com/...
        sb.setConfigLocation(null); // classpath:com/...
    }

}
