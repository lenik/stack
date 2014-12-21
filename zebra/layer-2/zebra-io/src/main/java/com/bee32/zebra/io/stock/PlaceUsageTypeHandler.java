package com.bee32.zebra.io.stock;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

import net.bodz.bas.db.batis.AbstractTypeHandler;

public class PlaceUsageTypeHandler
        extends AbstractTypeHandler<PlaceUsage> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PlaceUsage parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public PlaceUsage getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String name = rs.getString(columnName);
        return PlaceUsage.valueOf(name);
    }

    @Override
    public PlaceUsage getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String name = rs.getString(columnIndex);
        return PlaceUsage.valueOf(name);
    }

    @Override
    public PlaceUsage getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String name = cs.getString(columnIndex);
        return PlaceUsage.valueOf(name);
    }

}
