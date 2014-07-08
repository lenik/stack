package user.batis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.bee32.zebra.tk.sql.VhostDataService;

public class DbManager {

    VhostDataService service;

    void demo()
            throws SQLException {
        Connection connection = service.getConnection();
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            boolean hasUserTable = dbm.getTables(null, null, "user", null).next();
            if (!hasUserTable) {
                // UserMapper userMapper = service.getMapper(UserMapper.class);
                // userMapper.createUserTable();
            }
        } finally {
            connection.close();
        }
    }

}
