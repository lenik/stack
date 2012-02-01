package com.bee32.plover.faces.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 1，如果继承 MultiActionController，就必须约束方法名
 *
 * 2，可以用 Controller 接口实现复杂的地址转换
 *
 * 3，什么都不用时，默认会用什么实现地址转换？（未知） 在这种情况下可以构建 RESTful 地址。
 */
@Controller
@Lazy
public class RequestMappingTestController {

    @RequestMapping("/x/normal.do")
    public ModelAndView normal(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("/version");
    }

    @RequestMapping("/x/onlyReq.do")
    public ModelAndView onlyReq(HttpServletRequest req) {
        return new ModelAndView("/version");
    }

    @RequestMapping("/x/onlyResp.do")
    public ModelAndView onlyResp(HttpServletResponse resp) {
        return new ModelAndView("/version");
    }

    @RequestMapping("/x/empty.do")
    public ModelAndView empty() {
        return new ModelAndView("/version");
    }

    @RequestMapping("/x/twice.do")
    public ModelAndView twice(HttpServletResponse res1, HttpServletResponse res2) {
        return new ModelAndView("/version");
    }

}
