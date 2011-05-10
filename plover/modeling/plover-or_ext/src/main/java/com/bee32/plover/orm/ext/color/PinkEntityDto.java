package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import com.bee32.plover.orm.util.EntityDto;

public abstract class PinkEntityDto<E extends PinkEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public PinkEntityDto() {
        super();
    }

    public PinkEntityDto(E source) {
        super(source);
    }

    public PinkEntityDto(int selection) {
        super(selection);
    }

    public PinkEntityDto(int selection, E source) {
        super(selection, source);
    }

}
