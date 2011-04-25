package com.bee32.plover.orm.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

public class DataAccessExceptionUtil {

    public static void dumpAndThrow(DataAccessException dataAccessException)
            throws DataAccessException {

        Throwable spec = dataAccessException.getMostSpecificCause();

        if (spec instanceof SQLException) {
            SQLException sqle = (SQLException) spec;
            SQLException next;
            while ((next = sqle.getNextException()) != null) {
                System.err.println("The next exception in " + sqle);
                sqle.printStackTrace();
                sqle = next;
            }
        }

        throw dataAccessException;
    }

}
