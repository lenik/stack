package com.bee32.plover.orm.entity;

import java.util.Locale;

import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.arch.ComponentBuilder;
import com.bee32.plover.arch.ui.LazyAppearance;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.orm.util.ITypeAbbrAware;

/**
 * 数据对象
 *
 * <p lang="en">
 * Entity
 *
 * @see EntityResourceNS
 */
public class EntityResource
        extends Resource
        implements ITypeAbbrAware {

    ClassCatalog catalog;
    Class<?> entityClass;
    String member;

    public EntityResource(ClassCatalog catalog, Class<?> entityClass) {
        this(catalog, entityClass, null);
    }

    public EntityResource(ClassCatalog catalog, Class<?> entityClass, String member) {
        super(getLocalName(catalog, entityClass, member));
        this.catalog = catalog;
        this.entityClass = entityClass;
        this.member = member;

        // XXX locale override?
        Locale locale = Locale.getDefault();

        LazyAppearance appearance;
        if (entityClass != null) {
            appearance = new InjectedAppearance(entityClass, locale);
        } else {
            appearance = catalog.getAppearance();
        }
        // appearance.getPropertyDispatcher().dispatch();
        ComponentBuilder.setAppearance(this, appearance);
    }

    public ClassCatalog getCatalog() {
        return catalog;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getMember() {
        return member;
    }

    public static String getLocalName(ClassCatalog catalog, Class<?> clazz) {
        return getLocalName(catalog, clazz, null);
    }

    public static String getLocalName(ClassCatalog catalog, Class<?> clazz, String member) {
        if (catalog == null)
            throw new NullPointerException("catalog");

        StringBuilder sb = new StringBuilder();
        sb.append(catalog.getName());
        sb.append(":");
        if (clazz != null) {
            sb.append(ABBR.abbr(clazz));
            if (member != null) {
                sb.append(".");
                sb.append(member);
            }
        }
        return sb.toString();
    }

}
