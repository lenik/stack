package com.bee32.zebra.io.stock;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;

import net.bodz.bas.db.ibatis.TypeHandler;

public class PlaceUsageTypeHandler
        extends TypeHandler<PlaceUsage> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PlaceUsage parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter.getName(), Types.OTHER);
    }

    @Override
    public PlaceUsage getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String name = rs.getString(columnName);
        return PlaceUsage.METADATA.ofName(name);
    }

    @Override
    public PlaceUsage getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String name = rs.getString(columnIndex);
        return PlaceUsage.METADATA.ofName(name);
    }

    @Override
    public PlaceUsage getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String name = cs.getString(columnIndex);
        return PlaceUsage.METADATA.ofName(name);
    }

}
