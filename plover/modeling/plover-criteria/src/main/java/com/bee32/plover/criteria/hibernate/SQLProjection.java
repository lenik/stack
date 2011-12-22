package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;

public class SQLProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final String sql;
    final String groupBy;
    final String[] columnAliases;
    final Type[] types;

    public SQLProjection(String sql, String[] columnAliases, Type[] types) {
        this(sql, null, columnAliases, types);
    }

    public SQLProjection(String sql, String groupBy, String[] columnAliases, Type[] types) {
        this.sql = sql;
        this.groupBy = groupBy;
        this.columnAliases = columnAliases;
        this.types = types;
    }

    @Override
    protected Projection buildProjection() {
        if (groupBy == null)
            return Projections.sqlProjection(sql, columnAliases, types);
        else
            return Projections.sqlGroupProjection(sql, groupBy, columnAliases, types);
    }

    @Override
    public void format(StringBuilder out) {
        String quoted = "\"" + sql + "\"";
        out.append("(sql-projection ");
        out.append(quoted);
        if (columnAliases != null && columnAliases.length != 0) {
            out.append(" (column-alias ");
            for (String columnAlias : columnAliases) {
                out.append(" ");
                out.append(columnAlias);
            }
            out.append(")");
        }
        out.append(")");
    }

}
