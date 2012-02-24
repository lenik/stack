package com.bee32.icsf.access.web;

import java.io.Serializable;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.arch.util.ClassUtil;

public class EntityTypeDescriptor
        implements Serializable {

    private static final long serialVersionUID = 1L;

    // ClassCatalog catalog;
    String catalogName;
    Class<?> entityType;
    // String qualifiedName;
    String displayName;
    String description;

    public EntityTypeDescriptor(ClassCatalog catalog, Class<?> entityType) {
        // this.catalog = catalog;
        this.catalogName = catalog.getAppearance().getDisplayName();
        this.entityType = entityType;
        this.displayName = ClassUtil.getTypeName(entityType);
    }

    // public ClassCatalog getCatalog() {
    // return catalog;
    // }

    public String getCatalogName() {
        return catalogName;
    }

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
