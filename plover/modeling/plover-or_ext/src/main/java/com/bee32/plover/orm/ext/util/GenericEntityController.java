package com.bee32.plover.orm.ext.util;

import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.basic.EntityActionRequest;
import com.bee32.plover.orm.ext.basic.EntityActionResult;
import com.bee32.plover.orm.ext.basic.EntityHandler;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.context.ILocationConstants;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.plover.servlet.mvc.IActionHandler;

@ComponentTemplate
@Lazy
public abstract class GenericEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends BasicEntityController<E, K, Dto>
        implements ITypeAbbrAware, ILocationConstants {

    @Override
    protected ActionResult invokeHandler(IActionHandler handler, EntityActionRequest req, EntityActionResult result)
            throws Exception {

        HttpServletResponse resp = result.getResponse();

        String typeAbbr = req.getPathParameter();
        if (typeAbbr == null)
            throw new NullPointerException("typeAbbr");

        Class<? extends E> entityType;
        try {
            entityType = (Class<? extends E>) ABBR.expand(typeAbbr);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Qualified class name, but not existed " + typeAbbr, e);
        }

        if (entityType == null)
            throw new IllegalUsageException("Bad entity abbrev: " + typeAbbr);

        if (!Entity.class.isAssignableFrom(entityType))
            throw new IllegalUsageException("Not subclass of entity: " + entityType);

        if (handler instanceof EntityHandler<?, ?>) {
            @SuppressWarnings("unchecked")
            EntityHandler<E, K> eHandler = (EntityHandler<E, K>) handler;
            eHandler.setEntityType(entityType);
        }

        ActionResult ret = handler.handleRequest(req, result);

        ret = postfix("index", ret);
        return result;
    }

    protected ActionResult postfix(String defaultView, ActionResult result) {
        if (result == null) // End of response?
            return null;

        String viewName = result.getViewName();
        if (viewName != null && !viewName.startsWith(_prefix))
            return result;

        if (viewName == null)
            viewName = normalizeView(defaultView);
        else {
            assert _prefix.endsWith("/");
            String xbase = viewName.substring(_prefix.length());

            assert !xbase.startsWith("/");
            int slash = xbase.indexOf('/');
            if (slash == -1)
                // chop vn.
                // vn = vn.substring(0, vn.length() - 1);
                ;
            else
                // skip the first item.
                viewName += xbase.substring(0, slash + 1);
        }

        result.setViewName(viewName);
        return result;
    }

}