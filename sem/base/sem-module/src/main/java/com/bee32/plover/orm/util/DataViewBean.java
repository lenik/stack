package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.free.IllegalUsageException;

import com.bee32.icsf.access.AccessControlException;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.FriendData;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.faces.utils.FacesPartialContext;
import com.bee32.plover.faces.view.GenericViewBean;
import com.bee32.plover.faces.view.ViewMetadata;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.misc.IBeanIntro;

public abstract class DataViewBean
        extends GenericViewBean
        implements IBeanIntro {

    private static final long serialVersionUID = 1L;

    static final boolean normalizeSQL = true;

    protected static class ctx
            extends MixinnedDataAssembledContext {
        public static final FacesPartialContext view = FacesPartialContext.INSTANCE;
    }

    /**
     * Get the Data Access Object for specific entity type.
     *
     * @return Non-<code>null</code> data access object.
     * @throws IllegalUsageException
     *             If the given type is not subclass of entity type.
     */
    @SuppressWarnings("deprecation")
    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> DATA(Class<? extends E> entityType) {
        return ctx.data.access(entityType);
    }

    @Override
    public Object getIntro() {
        String _name = getClass().getCanonicalName();
        ViewMetadata metadata = BEAN(ViewMetadata.class);
        metadata.setAttribute(EntityPeripheralBean.CONTEXT_BEAN, this);
        return _name;
    }

    protected static <E extends Entity<K>, K extends Serializable> //
    E reloadEntity(E entity) {
        Class<? extends E> entityType = (Class<? extends E>) entity.getClass();
        K id = entity.getId();

        E reloaded = DATA(entityType).getOrFail(id);

        return reloaded;
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return ctx.data.reload(dto);
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int fmask) {
        return ctx.data.reload(dto, fmask);
    }

    protected <E extends Entity<?>, D extends EntityDto<E, ?>> //
    List<D> mrefList(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        try {
            List<E> entities = DATA(entityClass).list(criteriaElements);
            List<D> list = DTOs.mrefList(dtoClass, entities);
            return list;
        } catch (AccessControlException e) {
            reportException(e, "读取", entityClass);
            return Collections.emptyList();
        }
    }

    protected boolean reportException(AccessControlException e, String hint, Class<?> entityClass) {
        String entityName = ClassUtil.getTypeName(entityClass);
        uiLogger.error("您没有" + hint + entityName + "的权限。", e);
        return false;
    }

    protected String getBundledSQL(String name, Object... args) {
        String script = FriendData.script(getClass(), name + ".sql");
        for (int i = 0; i < args.length - 1; i += 2) {
            String key = String.valueOf(args[i]);
            Object value = args[i + 1];
            if (value == null)
                value = "";
            String replacement = String.valueOf(value);
            script = script.replace(key, replacement);
        }
        if (normalizeSQL)
            script = TextUtil.normalizeSpace(script);
        return script;
    }

}
