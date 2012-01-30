package com.bee32.plover.orm.upgrader.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.upgrader.IDatabaseMetadata;

public class PloverConfDatabaseMetadata
        implements IDatabaseMetadata {

    static Logger logger = LoggerFactory.getLogger(PloverConfDatabaseMetadata.class);

    int version;
    Date lastUpgradedDate;

    public PloverConfDatabaseMetadata() {
    }

    public void load(Connection connection)
            throws SQLException {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement
                        .executeQuery("select * from plover_conf where id in ('version', 'lastUpgradedDate'"); //
        ) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String value = resultSet.getString("value");

                switch (id) {
                case "version":
                    version = Integer.parseInt(value);
                    break;
                case "lastUpgradedDate":
                    try {
                        lastUpgradedDate = Dates.YYYY_MM_DD.parse(value);
                    } catch (ParseException e) {
                        logger.warn("Can't parse lastUpgradedDate: " + value);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Date getLastUpgradedDate() {
        return lastUpgradedDate;
    }

}
