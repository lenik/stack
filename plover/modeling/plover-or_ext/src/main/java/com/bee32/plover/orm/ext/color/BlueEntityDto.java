package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import com.bee32.plover.orm.util.EntityDto;

public abstract class BlueEntityDto<E extends BlueEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public BlueEntityDto() {
        super();
    }

    public BlueEntityDto(E source) {
        super(source);
    }

    public BlueEntityDto(int selection) {
        super(selection);
    }

    public BlueEntityDto(int selection, E source) {
        super(selection, source);
    }

}
