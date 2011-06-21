package com.bee32.plover.orm.ext.xp;

import java.io.Serializable;

import com.bee32.plover.orm.util.EntityDto;

public abstract class EntityExtDto<E extends EntityExt<K, X>, K extends Serializable, X extends XPool<?>>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public EntityExtDto() {
        super();
    }

    public EntityExtDto(int selection) {
        super(selection);
    }

}
