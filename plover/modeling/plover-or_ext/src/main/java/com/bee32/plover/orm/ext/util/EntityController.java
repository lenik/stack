package com.bee32.plover.orm.ext.util;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.servlet.mvc.CompositeController;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends CompositeController
        implements IEntityMarshalContext {

}
