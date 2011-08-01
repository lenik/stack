package com.bee32.plover.orm.ext.meta;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

// @Entity
@Blue
// @SequenceGenerator(name = "idgen", sequenceName = "entity_otf", allocationSize = 1)
public abstract class EntityOtf
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

}
