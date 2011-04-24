package com.bee32.sem.event.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.ext.util.EntityController;
import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.entity.EventPriority;

@RequestMapping(EventPriorityController.PREFIX + "*")
public class EventPriorityController
        extends EntityController<EventPriority, Integer, EventPriorityDto> {

    public static final String PREFIX = SEMEventModule.PREFIX + "priority/";

    @Override
    protected ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return null;
    }

    @Override
    protected ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return null;
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData data, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return null;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData data, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return null;
    }

}
