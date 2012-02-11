package com.bee32.plover.arch;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.site.scope.PerSite;

@Transactional(readOnly = true)
@ComponentTemplate
@PerSite
public abstract class DataService
        extends Component {

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    public DataService() {
        super();
    }

    public DataService(String serviceName) {
        super(serviceName);
    }

    protected <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getSelection());
    }

    protected <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int fmask) {
        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = DTOs.getEntityType(dto);
        K id = dto.getId();

        E reloaded = ctx.data.access(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, fmask, reloaded);
        return remarshalled;
    }

}
