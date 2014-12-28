package com.bee32.zebra.io.art;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

import net.bodz.bas.db.batis.MyBatisTypeHandler;

public class SupplyMethodTypeHandler
        extends MyBatisTypeHandler<SupplyMethod> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SupplyMethod parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public SupplyMethod getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String name = rs.getString(columnName);
        return SupplyMethod.valueOf(name);
    }

    @Override
    public SupplyMethod getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String name = rs.getString(columnIndex);
        return SupplyMethod.valueOf(name);
    }

    @Override
    public SupplyMethod getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String name = cs.getString(columnIndex);
        return SupplyMethod.valueOf(name);
    }

}
