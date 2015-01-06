package com.bee32.zebra.tk.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.bodz.bas.db.batis.IMapper;
import net.bodz.bas.db.batis.IMapperProvider;
import net.bodz.bas.db.batis.MybatisMapperProvider;
import net.bodz.bas.db.jdbc.BoneCPDataSourceProvider;
import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.db.jdbc.IDataSourceProvider;
import net.bodz.bas.rtx.AbstractQueryable;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.site.vhost.VirtualHostManager;

public class VhostDataService
        extends AbstractQueryable {

    public static final String ATTRIBUTE_KEY = VhostDataService.class.getName();

    private DataSourceArguments dataSourceArguments;
    private DataSource dataSource;
    private IMapperProvider mapperProvider;

    public VhostDataService(IVirtualHost vhost) {
        dataSourceArguments = vhost.getAttribute(DataSourceArguments.ATTRIBUTE_KEY);
        if (dataSourceArguments == null)
            throw new NullPointerException("dataSourceArguments");

        IDataSourceProvider dsp = vhost.getAttribute(IDataSourceProvider.ATTRIBUTE_KEY);
        if (dsp == null) {
            dsp = new BoneCPDataSourceProvider(dataSourceArguments);
            vhost.setAttribute(IDataSourceProvider.ATTRIBUTE_KEY, dsp);
        }

        dataSource = dsp.getDataSource();
        if (dataSource == null)
            throw new NullPointerException("dataSource");

        mapperProvider = vhost.getAttribute(IMapperProvider.ATTRIBUTE_KEY);
        if (mapperProvider == null) {
            mapperProvider = new MybatisMapperProvider(dataSource);
            vhost.setAttribute(IMapperProvider.ATTRIBUTE_KEY, mapperProvider);
        }
    }

    public Connection getConnection()
            throws SQLException {
        return dataSource.getConnection();
    }

    public IMapperProvider getMapperProvider() {
        return mapperProvider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <spec_t> spec_t query(Class<spec_t> specificationClass) {
        if (specificationClass == IMapperProvider.class)
            return (spec_t) mapperProvider;
        if (specificationClass == DataSource.class)
            return (spec_t) dataSource;
        if (IMapper.class.isAssignableFrom(specificationClass)) {
            Class<? extends IMapper> mapperClass = (Class<? extends IMapper>) specificationClass;
            return (spec_t) mapperProvider.getMapper(mapperClass);
        }
        return super.query(specificationClass);
    }

    public static VhostDataService getInstance() {
        IVirtualHost vhost = CurrentVirtualHost.getVirtualHostOpt();
        if (vhost == null)
            vhost = VirtualHostManager.getInstance().getVirtualHost("master");

        VhostDataService service = vhost.getAttribute(VhostDataService.ATTRIBUTE_KEY);
        if (service == null) {
            synchronized (VhostDataService.class) {
                if (service == null) {
                    service = new VhostDataService(vhost);
                    vhost.setAttribute(VhostDataService.ATTRIBUTE_KEY, service);
                }
            }
        }
        return service;
    }

}
