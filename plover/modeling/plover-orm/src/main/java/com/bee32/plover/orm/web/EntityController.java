package com.bee32.plover.orm.web;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.View;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.plover.servlet.mvc.CompositeController;
import com.bee32.plover.servlet.mvc.IActionHandler;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends CompositeController
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager;

    protected <_E extends Entity<? extends _K>, _K extends Serializable> //
    IEntityAccessService<_E, _K> asFor(Class<_E> entityType) {
        IEntityAccessService<_E, _K> service = dataManager.access(entityType);
        return service;
    }

    @Override
    protected ActionRequest newRequest(IActionHandler handler, HttpServletRequest request) {
        return new ActionRequest(handler, request);
    }

    @Override
    protected ActionResult newResult(String viewName) {
        return new ActionResult(viewName);
    }

    @Override
    protected ActionResult newResult(String viewName, Map<String, ?> model) {
        return new ActionResult(viewName, model);
    }

    @Override
    protected ActionResult newResult(View view) {
        return new ActionResult(view);
    }

    @Override
    protected ActionResult newResult(View view, Map<String, ?> model) {
        return new ActionResult(view, model);
    }

    @Override
    public <_E extends Entity<_K>, _K extends Serializable> _E loadEntity(Class<_E> entityType, _K id) {
        return dataManager.access(entityType).load(id);
    }

}
