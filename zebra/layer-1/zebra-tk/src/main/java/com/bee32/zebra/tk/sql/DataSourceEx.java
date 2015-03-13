package com.bee32.zebra.tk.sql;

import javax.sql.DataSource;

import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.bas.db.ibatis.IMapperProvider;
import net.bodz.bas.db.ibatis.IbatisMapperProvider;
import net.bodz.bas.db.jdbc.BoneCPDataSourceProvider;
import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.db.jdbc.IDataSourceProvider;
import net.bodz.bas.rtx.AbstractQueryable;

public class DataSourceEx
        extends AbstractQueryable {

    public static final String ATTRIBUTE_KEY = DataSourceEx.class.getName();

    private IDataSourceProvider dataSourceProvider;
    private DataSource dataSource;
    private IMapperProvider mapperProvider;

    public DataSourceEx(DataSourceArguments args) {
        if (args == null)
            throw new NullPointerException("args");

        dataSourceProvider = new BoneCPDataSourceProvider(args);

        dataSource = dataSourceProvider.getDataSource();
        if (dataSource == null)
            throw new NullPointerException("dataSource");

        mapperProvider = new IbatisMapperProvider(dataSource);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <spec_t> spec_t query(Class<spec_t> specificationClass) {
        if (specificationClass == null)
            throw new NullPointerException("specificationClass");
        if (specificationClass == IMapperProvider.class)
            return (spec_t) getMapperProvider();
        if (specificationClass == DataSource.class)
            return (spec_t) getDataSource();
        if (IMapper.class.isAssignableFrom(specificationClass)) {
            Class<? extends IMapper> mapperClass = (Class<? extends IMapper>) specificationClass;
            return (spec_t) getMapperProvider().getMapper(mapperClass);
        }
        return super.query(specificationClass);
    }

    public IDataSourceProvider getDataSourceProvider() {
        return dataSourceProvider;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public IMapperProvider getMapperProvider() {
        return mapperProvider;
    }

}
