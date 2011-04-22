package com.bee32.plover.web.faces.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class ACont {

    @RequestMapping("hey.htm")
    public ModelAndView hey(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("/version");
    }

}
