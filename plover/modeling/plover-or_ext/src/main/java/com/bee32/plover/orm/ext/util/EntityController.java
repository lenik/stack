package com.bee32.plover.orm.ext.util;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.View;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.basic.EntityActionRequest;
import com.bee32.plover.orm.ext.basic.EntityActionResult;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.plover.servlet.mvc.CompositeController;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends CompositeController
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager;

    @Override
    protected ActionRequest newRequest(HttpServletRequest request) {
        return new EntityActionRequest(request);
    }

    @Override
    protected ActionResult newResult(String viewName) {
        return new EntityActionResult(viewName);
    }

    @Override
    protected ActionResult newResult(String viewName, Map<String, ?> model) {
        return new EntityActionResult(viewName, model);
    }

    @Override
    protected ActionResult newResult(View view) {
        return new EntityActionResult(view);
    }

    @Override
    protected ActionResult newResult(View view, Map<String, ?> model) {
        return new EntityActionResult(view, model);
    }

    @Override
    public <_E extends Entity<_K>, _K extends Serializable> _E loadEntity(Class<_E> entityType, _K id) {
        return dataManager.load(entityType, id);
    }

}
