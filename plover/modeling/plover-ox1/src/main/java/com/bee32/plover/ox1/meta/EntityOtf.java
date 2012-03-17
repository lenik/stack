package com.bee32.plover.ox1.meta;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Blue;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "entity_otf", allocationSize = 1)
public abstract class EntityOtf
        extends CEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

}
