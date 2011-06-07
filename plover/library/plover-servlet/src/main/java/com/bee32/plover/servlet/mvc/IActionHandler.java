package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.IPriority;

public interface IActionHandler
        extends IPriority {

    String getPrefix();

    void setPrefix(String prefix);

    String getName();

    ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception;

}
