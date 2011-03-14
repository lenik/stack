package com.bee32.plover.orm.context;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
@Named("dataSourceTransactionManager")
@Lazy
public class PloverDataSourceTransactionManager
        extends DataSourceTransactionManager {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PloverDataSourceTransactionManager.class);

    public PloverDataSourceTransactionManager() {
        logger.info("Create plover data-source transaction manager");
    }

    @Inject
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

}
