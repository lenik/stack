package com.bee32.sem.frame.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuLoader;
import com.bee32.sem.frame.menu.SuperfishMenuBuilder;

@Controller
@Lazy
public class SuperfishMenuDemoController {

    @Inject
    MenuLoader menuLoader;

    @RequestMapping("sfmenuDemo.do")
    public ModelAndView sfmenuDemo(HttpServletRequest request, HttpServletResponse response) {

        String menuHtml = new SuperfishMenuBuilder(SEMFrameMenu.MAIN).toString();

        ModelAndView view = new ModelAndView();
        view.addObject("html", menuHtml);
        return view;
    }

}
