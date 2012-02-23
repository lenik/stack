package com.bee32.icsf.access.web;

import java.io.Serializable;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.arch.util.ClassUtil;

public class EntityTypeDescriptor
        implements Serializable {

    private static final long serialVersionUID = 1L;

    // ClassCatalog catalog;
    Class<?> entityType;
    // String qualifiedName;
    String displayName;
    String description;

    public EntityTypeDescriptor(ClassCatalog catalog, Class<?> entityType) {
        // this.catalog = catalog;
        this.entityType = entityType;
        displayName = ClassUtil.getTypeName(entityType);
    }

    // public ClassCatalog getCatalog() {
    // return catalog;
    // }

    public Class<?> getEntityType() {
        return entityType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

}
