package com.bee32.plover.orm.ext.dict;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.util.EntityController;

public abstract class DictController<E extends DictEntity<K>, K extends Serializable>
        extends EntityController<E, K> {

    @Override
    protected void preamble(Map<String, Object> metaData) {
    }

    @Override
    protected Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        return null;
    }

    @Override
    protected ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return null;
    }

}
