package com.bee32.plover.faces.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public abstract class ACont {

    @RequestMapping("hey.do")
    public ModelAndView hey(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("/version");
    }

    @RequestMapping("black.do")
    public abstract ModelAndView white(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

}
