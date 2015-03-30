package com.bee32.zebra.oa.calendar.impl;

import javax.persistence.Table;

import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;

public class TableMetadata {

    Class<?> entityClass;
    String label;

    String name;
    String catalog;
    String schema;

    // SchemaPref schemaPref;

    public TableMetadata(Class<?> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        this.entityClass = entityClass;
        IType type = PotatoTypes.getInstance().forClass(entityClass);
        iString label = type.getLabel();
        this.label = label == null ? name : label.toString();

        Table aTable = entityClass.getAnnotation(Table.class);
        if (aTable != null) {
            name = aTable.name();
            if (name.isEmpty())
                name = null;

            catalog = aTable.catalog();
            if (catalog.isEmpty())
                catalog = null;

            schema = aTable.schema();
            if (schema.isEmpty())
                schema = null;
        }

        if (name == null)
            name = entityClass.getSimpleName().toLowerCase();
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

}
