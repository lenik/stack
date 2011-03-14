package com.bee32.plover.orm;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public class PloverNamingStrategy
        extends ImprovedNamingStrategy {

    private static final long serialVersionUID = 1L;

    public String escapeName(String name) {
        if (name.startsWith("`"))
            return name;
        else
            return "`" + name + "`";
    }

    @Override
    public String classToTableName(String className) {
        return escapeName(super.classToTableName(className));
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return escapeName(super.propertyToColumnName(propertyName));
    }

    @Override
    public String tableName(String tableName) {
        return escapeName(super.tableName(tableName));
    }

    @Override
    public String columnName(String columnName) {
        return escapeName(super.columnName(columnName));
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity,
            String associatedEntityTable, String propertyName) {
        return escapeName(super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity,
                associatedEntityTable, propertyName));
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return escapeName(super.joinKeyColumnName(joinedColumn, joinedTable));
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName,
            String referencedColumnName) {
        return escapeName(super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName,
                referencedColumnName));
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        return escapeName(super.logicalColumnName(columnName, propertyName));
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable,
            String propertyName) {
        return escapeName(super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable,
                propertyName));
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return escapeName(super.logicalCollectionColumnName(columnName, propertyName, referencedColumn));
    }

    public static NamingStrategy getInstance(String dialect) {
        return new PloverNamingStrategy();
    }

}
