package com.bee32.zebra.tk.sql;

import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.err.IllegalRequestException;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;

public class VhostDataService {

    public static DataSourceEx forCurrentRequest() {
        IVirtualHost vhost = CurrentVirtualHost.getVirtualHostOpt();
        if (vhost == null)
            throw new IllegalRequestException("Virtual host is undefined.");
        else
            return forVirtualHost(vhost);
    }

    public static synchronized DataSourceEx forVirtualHost(IVirtualHost vhost) {
        DataSourceEx dataSource = vhost.getAttribute(DataSourceEx.ATTRIBUTE_KEY);
        if (dataSource == null) {
            DataSourceArguments args;
            args = vhost.getAttribute(DataSourceArguments.ATTRIBUTE_KEY);
            if (args == null)
                throw new IllegalUsageException("virtual host without data source args.");

            dataSource = vhost.getAttribute(DataSourceEx.ATTRIBUTE_KEY);
            if (dataSource == null) {
                dataSource = new DataSourceEx(args);
                vhost.setAttribute(DataSourceEx.ATTRIBUTE_KEY, dataSource);
            }
        }
        return dataSource;
    }

}
