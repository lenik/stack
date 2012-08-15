package com.bee32.plover.orm.web;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.View;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.plover.servlet.mvc.CompositeController;
import com.bee32.plover.servlet.mvc.IActionHandler;
import com.bee32.plover.site.scope.PerSite;

@ComponentTemplate
// @Controller
@PerSite
public abstract class EntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<? super E, K>>
        extends CompositeController {

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> DATA(Class<? extends E> entityType) {
        return ctx.data.access(entityType);
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

}
