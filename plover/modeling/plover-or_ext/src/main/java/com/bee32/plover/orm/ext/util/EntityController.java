package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.EntityBean;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends EntityBean<K>, K extends Serializable>
        extends _EntityController<E, K> {

    @RequestMapping("index.htm")
    public Map<String, Object> index(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._index(req, resp);
    }

    @RequestMapping("content.htm")
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._content(req, resp);
    }

    @RequestMapping("createForm.htm")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._createForm(req, resp);
    }

    @RequestMapping("editForm.htm")
    public ModelAndView editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._editForm(req, resp);
    }

    @RequestMapping("create.htm")
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._create(req, resp);
    }

    @RequestMapping("update.htm")
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._update(req, resp);
    }

    @RequestMapping("delete.htm")
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._delete(req, resp);
    }

}
