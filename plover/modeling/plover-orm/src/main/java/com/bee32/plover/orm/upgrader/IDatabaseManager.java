package com.bee32.plover.orm.upgrader;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseManager {

    /**
     * 获取 JDBC connection. 用完后必须关闭。
     *
     * @return Non-null JDBC connection object.
     */
    Connection getConnection()
            throws SQLException;

    /**
     * 获取数据库的当前版本等信息。
     *
     * @return Non-null {@link IDatabaseMetadata database metadata}.
     */
    IDatabaseMetadata getDatabaseMetadata()
            throws SQLException;

}
