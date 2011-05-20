package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.IPriority;

public interface IRequestHandler
        extends IPriority {

    int getPriority();

    /**
     * Could modify some request attributes.
     */
    boolean accept(HttpServletRequest request);

    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

}
