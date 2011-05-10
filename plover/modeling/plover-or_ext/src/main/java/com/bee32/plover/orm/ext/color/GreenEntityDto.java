package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

public abstract class GreenEntityDto<E extends GreenEntity<K>, K extends Serializable>
        extends UserFriendlyEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public GreenEntityDto() {
        super();
    }

    public GreenEntityDto(E source) {
        super(source);
    }

    public GreenEntityDto(int selection) {
        super(selection);
    }

    public GreenEntityDto(int selection, E source) {
        super(selection, source);
    }

}
