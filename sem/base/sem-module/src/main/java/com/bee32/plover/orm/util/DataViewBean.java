package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACLService;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.faces.view.GenericViewBean;
import com.bee32.plover.faces.view.ViewMetadata;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.sem.misc.IBeanIntro;

public abstract class DataViewBean
        extends GenericViewBean
        implements IEntityMarshalContext, IBeanIntro {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getIntro() {
        String _name = getClass().getCanonicalName();
        ViewMetadata metadata = ctx.getBean(ViewMetadata.class);
        metadata.setAttribute(EntityPeripheralBean.CONTEXT_BEAN, this);
        return _name;
    }

    static CommonDataManager getDataManager() {
        CommonDataManager dataManager = ctx.getBean(CommonDataManager.class);
        return dataManager;
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> serviceFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E getOrFail(Class<E> entityType, K id) {
        E entity = serviceFor(entityType).getOrFail(id);
        return entity;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E getRef(Class<E> entityType, K id) {
        E entity = serviceFor(entityType).lazyLoad(id);
        return entity;
    }

    protected static <E extends Entity<K>, K extends Serializable> //
    E reloadEntity(E entity) {
        Class<? extends E> entityType = (Class<? extends E>) entity.getClass();
        K id = entity.getId();

        E reloaded = serviceFor(entityType).getOrFail(id);

        return reloaded;
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getSelection());
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int fmask) {
        if (dto == null)
            throw new NullPointerException("dto");
        if (!dto.getMarshalType().isReference())
            return dto;

        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = dto.getEntityType();
        K id = dto.getId();

        E reloaded = serviceFor(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, fmask, reloaded);
        return remarshalled;
    }

    protected Set<Integer> getACLs(Permission minimum) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        IACLService aclService = ctx.getBean(IACLService.class);
        Set<Integer> acls = aclService.getACLs(currentUser, minimum);
        return acls;
    }

    protected <E extends Entity<?>, D extends EntityDto<E, ?>> //
    List<D> mrefList(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        List<E> entities = asFor(entityClass).list(criteriaElements);
        List<D> list = DTOs.mrefList(dtoClass, entities);
        return list;
    }

}
