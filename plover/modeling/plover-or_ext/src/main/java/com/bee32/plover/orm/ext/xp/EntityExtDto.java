package com.bee32.plover.orm.ext.xp;

import java.io.Serializable;

import com.bee32.plover.orm.ext.color.UIEntityDto;

public abstract class EntityExtDto<E extends EntityExt<K, X>, K extends Serializable, X extends XPool<?>>
        extends UIEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public EntityExtDto() {
        super();
    }

    public EntityExtDto(int selection) {
        super(selection);
    }

}
