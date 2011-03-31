package com.bee32.plover.web.faces.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class FaceletsSpringIntergration
        extends MultiActionController {

    @RequestMapping("/version.htm")
    public ModelAndView version(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView();
    }

}
