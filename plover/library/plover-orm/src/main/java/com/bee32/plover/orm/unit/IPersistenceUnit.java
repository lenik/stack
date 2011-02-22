package com.bee32.plover.orm.unit;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.arch.IComponent;

public interface IPersistenceUnit
        extends IComponent {

    /**
     * The persistence unit name.
     *
     * @return Non-<code>null</code> persistence unit name.
     */
    @Override
    String getName();

    /**
     * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean
     */
    Collection<Class<?>> getClasses();

}

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
