package com.bee32.plover.orm.ext.util;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.context.ILocationConstants;
import com.bee32.plover.servlet.mvc.ActionResult;

@ComponentTemplate
@Lazy
public abstract class GenericEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends BasicEntityController<E, K, Dto>
        implements ITypeAbbrAware, ILocationConstants {

    protected ActionResult postfix(String defaultView, ActionResult result) {
        if (result == null) // End of response?
            return null;

        String viewName = result.getViewName();
        if (viewName != null && !viewName.startsWith(_prefix))
            return result;

        if (viewName == null)
            viewName = req.normalizeView(defaultView);
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