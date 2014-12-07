package com.bee32.zebra.erp.stock;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

import net.bodz.bas.db.batis.AbstractTypeHandler;

public class CellUsageTypeHandler
        extends AbstractTypeHandler<CellUsage> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CellUsage parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public CellUsage getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String name = rs.getString(columnName);
        return CellUsage.valueOf(name);
    }

    @Override
    public CellUsage getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String name = rs.getString(columnIndex);
        return CellUsage.valueOf(name);
    }

    @Override
    public CellUsage getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String name = cs.getString(columnIndex);
        return CellUsage.valueOf(name);
    }

}
