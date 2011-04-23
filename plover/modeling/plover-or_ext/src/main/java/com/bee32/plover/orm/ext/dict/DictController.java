package com.bee32.plover.orm.ext.dict;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.ext.PloverORMExtModule;
import com.bee32.plover.orm.ext.util.GenericEntityController;

@Controller
public class DictController<E extends DictEntity<K>, K extends Serializable>
        extends GenericEntityController<E, K> {

    public static final String PREFIX = PloverORMExtModule.PREFIX + "1/";

    @Inject
    CommonDataManager dataManager;

    @Override
    protected ModelAndView _index(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return super._index(req, resp);
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData data, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("Form|Type: " + req.getAttribute("type"));
        return null;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData data, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("createOrEdit|Type: " + req.getAttribute("type"));
        return null;
    }

}
