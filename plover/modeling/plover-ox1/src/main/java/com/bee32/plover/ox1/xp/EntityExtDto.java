package com.bee32.plover.ox1.xp;

import java.io.Serializable;

import com.bee32.plover.ox1.color.UIEntityDto;

public abstract class EntityExtDto<E extends EntityExt<K, X>, K extends Serializable, X extends XPool<?>>
        extends UIEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public EntityExtDto() {
        super();
    }

    public EntityExtDto(int fmask) {
        super(fmask);
    }

}
