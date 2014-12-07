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
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.site.vhost.VirtualHostManager;

public class VhostDataService
        implements IMapperProvider {

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

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection()
            throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public <T extends IMapper> T getMapper(Class<T> mapperClass) {
        return mapperProvider.getMapper(mapperClass);
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
