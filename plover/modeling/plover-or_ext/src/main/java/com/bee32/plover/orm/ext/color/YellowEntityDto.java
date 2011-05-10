package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import com.bee32.plover.orm.util.EntityDto;

public abstract class YellowEntityDto<E extends YellowEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public YellowEntityDto() {
        super();
    }

    public YellowEntityDto(E source) {
        super(source);
    }

    public YellowEntityDto(int selection) {
        super(selection);
    }

    public YellowEntityDto(int selection, E source) {
        super(selection, source);
    }


}
