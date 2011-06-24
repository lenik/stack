package com.bee32.plover.orm.entity;

import java.util.Locale;

import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.arch.ComponentBuilder;
import com.bee32.plover.arch.ui.res.InjectedAppearance;

/**
 * @see EntityResourceNS
 */
public class EntityResource
        extends Resource {

    Class<?> entityClass;
    String member;

    public EntityResource(Class<?> entityClass) {
        super(getEntityName(entityClass));
        this.entityClass = entityClass;
        init();
    }

    public EntityResource(Class<?> entityClass, String member) {
        super(getEntityName(entityClass, member));
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

    static String getEntityName(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        String name = clazz.getSimpleName();
        return name;
    }

    static String getEntityName(Class<?> clazz, String member) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (member == null)
            throw new NullPointerException("member");

        String baseName = getEntityName(clazz);
        return baseName + "." + member;
    }

}
