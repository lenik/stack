package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;

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

    @RequestMapping("save.htm")
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _save(req, resp);
    }

    @RequestMapping("update.htm")
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _update(req, resp);
    }

    @RequestMapping("delete.htm")
    public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _delete(req, resp);
    }

}
