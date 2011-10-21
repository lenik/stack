package com.bee32.plover.web.faces.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.inject.ControllerTemplate;
import com.bee32.plover.servlet.util.ServletDiag;

@ControllerTemplate
@Lazy
@RequestMapping("/bcont/*")
public class BCont
        extends ACont {

    @RequestMapping("bye.*")
    public ModelAndView goodbye(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("/version");
    }

    @Override
    public ModelAndView white(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.err.println("In white()!");
        return ServletDiag.dump(req, resp);
    }

}
