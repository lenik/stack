package com.bee32.plover.orm.ext.basic;

import com.bee32.plover.orm.ext.util.EntityAction;

public class SimpleEntityDao {

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
