package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.util.TransferBy;
import com.bee32.plover.servlet.context.ILocationConstants;

@ComponentTemplate
@Lazy
public abstract class GenericEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends EntityController<E, K, Dto>
        implements ITypeAbbrAware, ILocationConstants {

    static ThreadLocal<RequestGenerics> tlRequestGenerics = new ThreadLocal<RequestGenerics>();

    public GenericEntityController() {
    }

    @Override
    protected Class<? extends K> getKeyType() {
        return tlRequestGenerics.get().getKeyType();
    }

    @Override
    protected Class<? extends E> getEntityType() {
        return tlRequestGenerics.get().<E, K> getEntityType();
    }

    @Override
    protected Class<? extends Dto> getTransferType() {
        return tlRequestGenerics.get().<Dto, E, K> getTransferType();
    }

    protected void preinit(String type, HttpServletRequest req, HttpServletResponse resp) {
        if (type == null)
            throw new NullPointerException("typeAbbr");

        Class<? extends E> entityType;
        try {
            entityType = (Class<? extends E>) ABBR.expand(type);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Qualified class name, but not existed " + type, e);
        }

        if (entityType == null)
            throw new IllegalUsageException("Bad entity abbrev: " + type);

        if (!Entity.class.isAssignableFrom(entityType))
            throw new IllegalUsageException("Not subclass of entity: " + entityType);

        RequestGenerics requestGenerics = new RequestGenerics();
        requestGenerics.entityType = entityType;
        requestGenerics.keyType = EntityUtil.getKeyType(entityType);

        TransferBy transferBy = entityType.getAnnotation(TransferBy.class);
        if (transferBy != null)
            requestGenerics.transferType = transferBy.value();
        else
            // fallback to the declared Dto.
            requestGenerics.transferType = ClassUtil.infer1(getClass(), GenericEntityController.class, 2);

        tlRequestGenerics.set(requestGenerics);
    }

    protected ModelAndView postfix(String defaultView, ModelAndView modelAndView) {
        if (modelAndView == null) // End of response?
            return null;

        String vn = modelAndView.getViewName();
        if (vn != null && !vn.startsWith(_prefix))
            return modelAndView;

        if (vn == null)
            vn = normalizeView(defaultView);
        else {
            assert _prefix.endsWith("/");
            String xbase = vn.substring(_prefix.length());

            assert !xbase.startsWith("/");
            int slash = xbase.indexOf('/');
            if (slash == -1)
                // chop vn.
                // vn = vn.substring(0, vn.length() - 1);
                ;
            else
                // skip the first item.
                vn += xbase.substring(0, slash + 1);
        }

        modelAndView.setViewName(vn);
        return modelAndView;
    }

    @RequestMapping("{type}/index.htm")
    public ModelAndView index(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _index(req, resp);
        return postfix("index", view);
    }

}
}