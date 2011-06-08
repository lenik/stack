package com.bee32.plover.orm.ext.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public abstract class BasicEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends EntityController<E, K, Dto> {

    static Logger logger = LoggerFactory.getLogger(BasicEntityController.class);

    protected boolean _createOTF;

}
