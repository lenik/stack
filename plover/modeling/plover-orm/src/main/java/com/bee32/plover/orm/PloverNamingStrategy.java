package com.bee32.plover.orm;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PloverNamingStrategy
        extends ImprovedNamingStrategy {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PloverNamingStrategy.class);

    static boolean byCallerMethod = true;

    public String escapeName(String name) {
        String method = null;
        if (byCallerMethod) {
            StackTraceElement[] st = Thread.currentThread().getStackTrace();
            method = st[2].getMethodName();
            logger.debug(method + ": " + name);
        }
        name = name.replace("`", "");
        return "`" + name + "`";
    }

    @Override
    public String classToTableName(String className) {
        String name = super.classToTableName(className);
        return escapeName(name);
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        String name = super.propertyToColumnName(propertyName);
        return escapeName(name);
    }

    @Override
    public String tableName(String tableName) {
        String name = super.tableName(tableName);
        return escapeName(name);
    }

    @Override
    public String columnName(String columnName) {
        String name = super.columnName(columnName);
        return escapeName(name);
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity,
            String associatedEntityTable, String propertyName) {
        String name = super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable,
                propertyName);
        return escapeName(name);
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        String name = super.joinKeyColumnName(joinedColumn, joinedTable);
        return escapeName(name);
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName,
            String referencedColumnName) {
        String name = super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName,
                referencedColumnName);
        return escapeName(name);
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        String name = super.logicalColumnName(columnName, propertyName);
        return escapeName(name);
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String assocEntityTable,
            String propertyName) {
        String name = super.logicalCollectionTableName(tableName, ownerEntityTable, assocEntityTable, propertyName);
        return escapeName(name);
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        String name = super.logicalCollectionColumnName(columnName, propertyName, referencedColumn);
        return escapeName(name);
    }

    static PloverNamingStrategy defaultInstance = new PloverNamingStrategy();

    public static NamingStrategy getInstance(String dialect) {
        return defaultInstance;
    }

    public static PloverNamingStrategy getDefaultInstance() {
        return defaultInstance;
    }

}
