package com.bee32.sem.frame.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.servlet.util.ServletDiag;

@Controller
@Lazy
@RequestMapping("/test/*")
public class FrameTestController {

    @RequestMapping("firstView.do")
    public String firstView() {
        return "test/jqmix";
    }

    @RequestMapping("jqmix.do")
    public String jqmix(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ServletDiag.dump(req, resp);
        return null;
    }

}
