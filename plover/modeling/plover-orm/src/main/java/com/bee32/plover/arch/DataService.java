package com.bee32.plover.arch;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.MixinnedDataAssembledContext;
import com.bee32.plover.site.scope.PerSite;

@Transactional(readOnly = true)
@ComponentTemplate
@PerSite
public abstract class DataService
        extends Component {

    protected static class ctx
            extends MixinnedDataAssembledContext {
    }

    @SuppressWarnings("deprecation")
    protected static Object BEAN(String name)
            throws BeansException {
        return ctx.bean.getBean(name);
    }

    @SuppressWarnings("deprecation")
    protected static <T> T BEAN(String name, Class<T> requiredType)
            throws BeansException {
        return ctx.bean.getBean(name, requiredType);
    }

    @SuppressWarnings("deprecation")
    protected static <T> T BEAN(Class<T> requiredType)
            throws BeansException {
        return ctx.bean.getBean(requiredType);
    }

    @SuppressWarnings("deprecation")
    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> DATA(Class<? extends E> entityType) {
        return ctx.data.access(entityType);
    }

    public DataService() {
        super();
    }

    public DataService(String serviceName) {
        super(serviceName);
    }

    protected <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getFmask());
    }

    protected <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int fmask) {
        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = DTOs.getEntityType(dto);
        K id = dto.getId();

        E reloaded = DATA(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, fmask, reloaded);
        return remarshalled;
    }

}
