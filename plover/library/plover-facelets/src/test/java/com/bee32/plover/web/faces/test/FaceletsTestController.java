package com.bee32.plover.web.faces.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.servlet.util.ServletDiag;

@Controller
@Lazy
public class FaceletsTestController {

    @RequestMapping("/version.htm")
    public ModelAndView version(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView();
    }

    @RequestMapping("/info.htm")
    public void info(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ServletDiag.dump(req, resp);
    }

}
