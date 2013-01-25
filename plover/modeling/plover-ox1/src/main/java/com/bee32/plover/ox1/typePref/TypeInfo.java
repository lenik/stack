package com.bee32.plover.ox1.typePref;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.config.SiteSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.ITypeAbbrAware;

public class TypeInfo
        implements ITypeAbbrAware, Serializable {

    private static final long serialVersionUID = 1L;

    final Class<?> clazz;
    final String typeId;

    public TypeInfo(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
        this.typeId = ABBR.abbr(clazz);
    }

    public Class<?> getType() {
        return clazz;
    }

    public String getId() {
        return typeId;
    }

    public String getDisplayName() {
        return ClassUtil.getTypeName(clazz);
    }

    static List<TypeInfo> entityTypes;

    public static List<TypeInfo> getEntityTypes() {
        if (entityTypes == null) {
            synchronized (TypeInfo.class) {
                if (entityTypes == null) {
                    entityTypes = getEntityTypes(new Class<?>[0]);
                }
            }
        }
        return entityTypes;

    }

    public static List<TypeInfo> getEntityTypes(Class<?>... interfaces) {
        PersistenceUnit unit = SiteSessionFactoryBean.getForceUnit();
        Set<Class<?>> classes = unit.getClasses();
        List<TypeInfo> entityTypes = new ArrayList<TypeInfo>(/* classes.size() */);
        for (Class<?> clazz : classes) {
            boolean interesting = true;
            for (Class<?> iface : interfaces)
                if (!iface.isAssignableFrom(clazz)) {
                    interesting = false;
                    break;
                }
            if (!interesting)
                continue;
            TypeInfo type = new TypeInfo(clazz);
            entityTypes.add(type);
        }
        return entityTypes;
    }

}
