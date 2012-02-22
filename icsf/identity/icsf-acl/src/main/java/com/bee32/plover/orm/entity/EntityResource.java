package com.bee32.plover.orm.entity;

import java.util.Locale;

import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.arch.ComponentBuilder;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.orm.util.ITypeAbbrAware;

/**
 * @see EntityResourceNS
 */
public class EntityResource
        extends Resource
        implements ITypeAbbrAware {

    String catalogName;
    Class<?> entityClass;
    String member;

    public EntityResource(ClassCatalog catalog, Class<?> entityClass) {
        super(getLocalName(catalog, entityClass));
        this.catalogName = catalog.getName();
        this.entityClass = entityClass;
        init();
    }

    public EntityResource(ClassCatalog catalog, Class<?> entityClass, String member) {
        super(getLocalName(catalog, entityClass, member));
        this.catalogName = catalog.getName();
        this.entityClass = entityClass;
        this.member = member;
        init();
    }

    void init() {
        Locale locale = Locale.getDefault();
        InjectedAppearance appearance = new InjectedAppearance(entityClass, locale);
        // appearance.getPropertyDispatcher().dispatch();
        ComponentBuilder.setAppearance(this, appearance);
    }

    public String getCatalogName() {
        return catalogName;
    }

    public static String getLocalName(ClassCatalog catalog, Class<?> clazz) {
        return getLocalName(catalog, clazz, null);
    }

    public static String getLocalName(ClassCatalog catalog, Class<?> clazz, String member) {
        if (catalog == null)
            throw new NullPointerException("catalog");
        if (clazz == null)
            throw new NullPointerException("clazz");

        StringBuilder sb = new StringBuilder();
        sb.append(catalog.getName());
        sb.append(":");
        sb.append(ABBR.abbr(clazz));
        if (member != null) {
            sb.append(".");
            sb.append(member);
        }
        return sb.toString();
    }

}
