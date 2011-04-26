package com.bee32.plover.arch;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

@Transactional(readOnly = true)
@ComponentTemplate
@Lazy
public abstract class EnterpriseService
        extends Component {

    public EnterpriseService() {
        super();
    }

    public EnterpriseService(String serviceName) {
        super(serviceName);
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> D marshal(
            Class<D> dtoClass, E entity) {
        if (entity == null)
            return null;
        else
            return DTOs.marshal(dtoClass, entity);
    }

}
