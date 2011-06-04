package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.IPriority;

public interface IActionHandler
        extends IPriority {

    String getName();

    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

}
