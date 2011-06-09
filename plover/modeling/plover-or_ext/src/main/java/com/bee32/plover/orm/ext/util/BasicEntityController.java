package com.bee32.plover.orm.ext.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public abstract class BasicEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends EntityController<E, K, Dto> {

    static Logger logger = LoggerFactory.getLogger(BasicEntityController.class);

    // protected boolean _createOTF;

    public BasicEntityController() {

    }

    protected void doAction(EntityAction action, E entity, Dto dto, Object... args) {
        switch (action.getType()) {
        case CREATE:
            // fillTemplate(dto);
            break;

        case LOAD:
            dto.marshal(this, entity);
            break;

        case SAVE:
            dto.unmarshalTo(this, entity);
            break;
        }
    }

}
