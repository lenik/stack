package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;

public class DTOs
        extends EntityDto<Entity<Serializable>, Serializable> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(Entity<Serializable> source) {
    }

    @Override
    protected void _unmarshalTo(Entity<Serializable> target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
