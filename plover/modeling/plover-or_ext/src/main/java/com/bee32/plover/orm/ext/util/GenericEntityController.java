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
import com.bee32.plover.orm.util.ITypeAbbrAware;

@ComponentTemplate
@Lazy
public abstract class GenericEntityController<E extends EntityBean<K>, K extends Serializable>
        extends _EntityController<E, K>
        implements ITypeAbbrAware {

    protected static final String TYPE = "type";

    protected void initType(HttpServletRequest req, String typeAbbr) {
        if (typeAbbr == null)
            throw new NullPointerException("typeAbbr");

        Class<?> type = ABBR.expand(typeAbbr);
        if (type == null)
            throw new IllegalUsageException("Unknown entity type: " + typeAbbr);

        if (!EntityBean.class.isAssignableFrom(type))
            throw new IllegalUsageException("Not subclass of entity: " + type);

        req.setAttribute(TYPE, type);
    }

    @RequestMapping("{type}/index.htm")
    public ModelAndView index(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _index(req, resp);
    }

    @RequestMapping("{type}/content.htm")
    public ModelAndView content(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _content(req, resp);
    }

    @RequestMapping("{type}/data.htm")
    public ModelAndView data(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _data(req, resp);
    }

    @RequestMapping("{type}/createForm.htm")
    public ModelAndView createForm(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _createForm(req, resp);
    }

    @RequestMapping("(type)/create.htm")
    public ModelAndView create(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _create(req, resp);
    }

    @RequestMapping("{type}/editForm.htm")
    public ModelAndView editForm(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _editForm(req, resp);
    }

    @RequestMapping("{type}/edit.htm")
    public ModelAndView edit(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _edit(req, resp);
    }

    @RequestMapping("{type}/delete.htm")
    public ModelAndView delete(@PathVariable String type, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req, type);
        return _delete(req, resp);
    }

}
