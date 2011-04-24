package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.util.EntityDto;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends EntityBean<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends _EntityController<E, K, Dto> {

    private final Class<E> entityType;
    private final Class<K> keyType;
    private final Class<Dto> transferType;

    public EntityController() {
        Type[] typeArgs = ClassUtil.getTypeArgs(getClass(), EntityController.class);
        entityType = ClassUtil.bound1(typeArgs[0]);
        keyType = ClassUtil.bound1(typeArgs[1]);
        transferType = ClassUtil.bound1(typeArgs[2]);
    }

    @Override
    protected Class<? extends E> getEntityType() {
        return entityType;
    }

    @Override
    protected Class<? extends K> getKeyType() {
        return keyType;
    }

    @Override
    protected Class<? extends Dto> getTransferType() {
        return transferType;
    }

    @RequestMapping("index.htm")
    public ModelAndView index(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _index(req, resp);
    }

    @RequestMapping("content.htm")
    public ModelAndView content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _content(req, resp);
    }

    @RequestMapping("data.htm")
    public ModelAndView data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _data(req, resp);
    }

    @RequestMapping("createForm.htm")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _createForm(req, resp);
    }

    @RequestMapping("editForm.htm")
    public ModelAndView editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _editForm(req, resp);
    }

    @RequestMapping("create.htm")
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _create(req, resp);
    }

    @RequestMapping("edit.htm")
    public ModelAndView edit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _edit(req, resp);
    }

    @RequestMapping("delete.htm")
    public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _delete(req, resp);
    }

}
