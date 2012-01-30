package com.bee32.plover.orm.upgrader.impl;

import java.sql.SQLException;

import com.bee32.plover.orm.upgrader.IDatabaseManager;
import com.bee32.plover.orm.upgrader.IDatabaseMetadata;
import com.bee32.plover.orm.upgrader.IDatabaseUpgrader;

public class DefaultDatabaseUpgrader
        implements IDatabaseUpgrader {

    @Override
    public void upgrade(IDatabaseManager databaseManager)
            throws SQLException {
        IDatabaseMetadata metadata = databaseManager.getDatabaseMetadata();
    }

}
