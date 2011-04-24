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

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.util.TransferBy;
import com.bee32.plover.servlet.context.LocationContextConstants;

@ComponentTemplate
@Lazy
public abstract class GenericEntityController<E extends EntityBean<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends _EntityController<E, K, Dto>
        implements ITypeAbbrAware, LocationContextConstants {

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

        if (!EntityBean.class.isAssignableFrom(entityType))
            throw new IllegalUsageException("Not subclass of entity: " + entityType);

        RequestGenerics requestGenerics = new RequestGenerics();
        requestGenerics.entityType = entityType;
        requestGenerics.keyType = EntityUtil.getKeyType(entityType);

        TransferBy transferBy = entityType.getAnnotation(TransferBy.class);
        if (transferBy != null)
            requestGenerics.transferType = transferBy.value();
        else
            requestGenerics.transferType = null;

        tlRequestGenerics.set(requestGenerics);
    }

    protected ModelAndView postfix(String defaultView, ModelAndView modelAndView) {
        if (modelAndView == null) // End of response?
            return null;

        String vn = modelAndView.getViewName();
        if (vn != null && !vn.startsWith(prefix))
            return modelAndView;

        if (vn == null)
            vn = viewOf(defaultView);
        else {
            assert prefix.endsWith("/");
            String xbase = vn.substring(prefix.length());

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

    @RequestMapping("{type}/content.htm")
    public ModelAndView content(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _content(req, resp);
        return postfix("content", view);
    }

    @RequestMapping("{type}/data.htm")
    public ModelAndView data(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _data(req, resp);
        return postfix("data", view);
    }

    @RequestMapping("{type}/createForm.htm")
    public ModelAndView createForm(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _createForm(req, resp);
        return postfix("createForm", view);
    }

    @RequestMapping("{type}/create.htm")
    public ModelAndView create(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _create(req, resp);
        return postfix("create", view);
    }

    @RequestMapping("{type}/editForm.htm")
    public ModelAndView editForm(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _editForm(req, resp);
        return postfix("editForm", view);
    }

    @RequestMapping("{type}/edit.htm")
    public ModelAndView edit(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _edit(req, resp);
        return postfix("edit", view);
    }

    @RequestMapping("{type}/delete.htm")
    public ModelAndView delete(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        preinit(type, req, resp);
        ModelAndView view = _delete(req, resp);
        return postfix("delete", view);
    }

}

class RequestGenerics {

    Class<? extends EntityBean<? extends Serializable>> entityType;
    Class<? extends Serializable> keyType;
    Class<? extends EntityDto<? extends EntityBean<? extends Serializable>, ? extends Serializable>> transferType;

    @SuppressWarnings("unchecked")
    public <E extends EntityBean<K>, K extends Serializable> Class<E> getEntityType() {
        return (Class<E>) entityType;
    }

    public <E extends EntityBean<K>, K extends Serializable> void setEntityType(Class<E> entityType) {
        this.entityType = entityType;
    }

    @SuppressWarnings("unchecked")
    public <K extends Serializable> K getKeyType() {
        return (K) keyType;
    }

    public <K extends Serializable> void setKeyType(Class<K> keyType) {
        this.keyType = keyType;
    }

    @SuppressWarnings("unchecked")
    public <Dto extends EntityDto<E, K>, E extends EntityBean<K>, K extends Serializable> Class<Dto> getTransferType() {
        return (Class<Dto>) transferType;
    }

    public <Dto extends EntityDto<E, K>, E extends EntityBean<K>, K extends Serializable> void setTransferType(
            Class<Dto> transferType) {
        this.transferType = transferType;
    }

}