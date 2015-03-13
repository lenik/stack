package com.bee32.zebra.tk.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.log.Logger;
import net.bodz.bas.log.LoggerFactory;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.site.vhost.MutableVirtualHost;
import net.bodz.bas.site.vhost.MutableVirtualHostResolver;

public class VirtualHostTemplate
        extends MutableVirtualHostResolver {

    static final Logger logger = LoggerFactory.getLogger(VirtualHostTemplate.class);

    private DataSourceEx master;
    private DataSourceArguments templatedb = new DataSourceArguments();

    public VirtualHostTemplate(DataSourceEx master) {
        if (master == null)
            throw new NullPointerException("master");
        this.master = master;

        templatedb.setServer("localhost:1063");
        templatedb.setDatabase("<not-set>");
        templatedb.setUserName("semsadmin");
        templatedb.setPassword("MxDkUWl1");
    }

    @Override
    public IVirtualHost get(String id) {
        if ("v3".equals(id))
            id = "zjhf";
        return super.get(id);
    }

    @Override
    public synchronized IVirtualHost resolve(HttpServletRequest request) {
        IVirtualHost vhost = super.resolve(request);
        if (vhost == null)
            vhost = findAndAdd(request);
        return vhost;
    }

    public IVirtualHost findAndAdd(HttpServletRequest request) {
        // TODO request.getHeader("X-Forward-...");
        String hostName = request.getServerName();

        int dot = hostName.indexOf('.');
        String firstName = dot == -1 ? hostName : hostName.substring(0, dot);

        String id = firstName.toLowerCase();
        IVirtualHost vhostById = get(id);
        if (vhostById != null)
            return vhostById;

        if (!checkDatabaseExists(id))
            return null;

        MutableVirtualHost vhost = new MutableVirtualHost();
        vhost.setName(id);
        vhost.addHostSpecifier(hostName);

        DataSourceArguments args = templatedb.clone();
        args.setDatabase(id);
        vhost.setAttribute(DataSourceArguments.ATTRIBUTE_KEY, args);

        add(vhost);
        return vhost;
    }

    boolean checkDatabaseExists(String name) {
        if (name == null)
            throw new NullPointerException("name");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = master.getDataSource().getConnection();

            ps = connection.prepareStatement("select * from pg_database where datname=?");
            ps.setString(1, name);

            rs = ps.executeQuery();

            if (!rs.next())
                return false;

            String datname = rs.getString("datname");
            assert name.equals(datname);

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
    }

    void close(AutoCloseable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (Exception e) {
                logger.error(e, "Can't close " + closeable);
            }
    }

}
