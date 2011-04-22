package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
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

    protected void initType(HttpServletRequest req) {
        String typeAbbr = req.getParameter("type");
        if (typeAbbr == null)
            throw new NullPointerException("typeAbbr");

        Class<?> type = ABBR.expand(typeAbbr);
        if (type == null)
            throw new IllegalUsageException("Unknown entity type: " + typeAbbr);

        if (!EntityBean.class.isAssignableFrom(type))
            throw new IllegalUsageException("Not subclass of entity: " + type);

        req.setAttribute(TYPE, type);
    }

    @RequestMapping("*/index.htm")
    public Map<String, Object> index(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
// initType(req);
// return super._index(req, resp);
        return null;
    }

    @RequestMapping("*/content.htm")
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req);
        return super._content(req, resp);
    }

    @RequestMapping("*/data.htm")
    public void data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super._data(req, resp);
    }

    @RequestMapping("*/createForm.htm")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req);
        return super._createForm(req, resp);
    }

    @RequestMapping("(type)/create.htm")
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req);
        return super._create(req, resp);
    }

    @RequestMapping("*/editForm.htm")
    public ModelAndView editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req);
        return super._editForm(req, resp);
    }

    @RequestMapping("*/update.htm")
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req);
        return super._update(req, resp);
    }

    @RequestMapping("*/delete.htm")
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        initType(req);
        return super._delete(req, resp);
    }

}
