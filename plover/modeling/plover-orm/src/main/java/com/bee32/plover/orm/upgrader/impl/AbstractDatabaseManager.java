package com.bee32.plover.orm.upgrader.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.bee32.plover.orm.upgrader.IDatabaseManager;
import com.bee32.plover.orm.upgrader.IDatabaseMetadata;

public abstract class AbstractDatabaseManager
        implements IDatabaseManager {

    @Override
    public IDatabaseMetadata getDatabaseMetadata()
            throws SQLException {
        PloverConfDatabaseMetadata metadata = new PloverConfDatabaseMetadata();
        try (Connection connection = getConnection()) {
            metadata.load(connection);
        }
        return metadata;
    }

}
