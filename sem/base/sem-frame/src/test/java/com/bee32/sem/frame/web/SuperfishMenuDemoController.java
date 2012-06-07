package com.bee32.sem.frame.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.sem.frame.menu.SuperfishMenuBuilder;

@Controller
@Lazy
public class SuperfishMenuDemoController {

    @RequestMapping("sfmenuDemo.do")
    public ModelAndView sfmenuDemo(HttpServletRequest request, HttpServletResponse response) {

        String menuHtml = new SuperfishMenuBuilder(null/* main-menu */).toString();

        ModelAndView view = new ModelAndView();
        view.addObject("html", menuHtml);
        return view;
    }

}
