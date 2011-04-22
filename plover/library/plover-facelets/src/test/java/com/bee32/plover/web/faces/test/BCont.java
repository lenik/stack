package com.bee32.plover.web.faces.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Lazy
@RequestMapping("/bcont/*")
public class BCont
        extends ACont {

    @RequestMapping("bye.htm")
    public ModelAndView goodbye(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("/version");
    }

}
