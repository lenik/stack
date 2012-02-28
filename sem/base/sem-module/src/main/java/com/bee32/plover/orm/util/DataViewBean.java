package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.bee32.icsf.access.AccessControlException;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACLService;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.faces.view.GenericViewBean;
import com.bee32.plover.faces.view.ViewBean;
import com.bee32.plover.faces.view.ViewMetadata;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.sem.misc.IBeanIntro;

public abstract class DataViewBean
        extends GenericViewBean
        implements IBeanIntro {

    private static final long serialVersionUID = 1L;

    protected static class ctx
            extends ViewBean.ctx {
        public static final WiredDataPartialContext data = new WiredDataPartialContext(bean);
    }

    @Override
    public Object getIntro() {
        String _name = getClass().getCanonicalName();
        ViewMetadata metadata = ctx.bean.getBean(ViewMetadata.class);
        metadata.setAttribute(EntityPeripheralBean.CONTEXT_BEAN, this);
        return _name;
    }

    protected static <E extends Entity<K>, K extends Serializable> //
    E reloadEntity(E entity) {
        Class<? extends E> entityType = (Class<? extends E>) entity.getClass();
        K id = entity.getId();

        E reloaded = ctx.data.access(entityType).getOrFail(id);

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

    protected Set<Integer> getACLs(Permission minimum) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        IACLService aclService = ctx.bean.getBean(IACLService.class);
        Set<Integer> acls = aclService.getACLs(currentUser, minimum);
        return acls;
    }

    protected <E extends Entity<?>, D extends EntityDto<E, ?>> //
    List<D> mrefList(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        try {
            List<E> entities = ctx.data.access(entityClass).list(criteriaElements);
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

}
