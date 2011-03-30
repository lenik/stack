package com.bee32.sem.frame.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.sem.frame.builtins.MainMenu;
import com.bee32.sem.frame.menu.SuperFishMenuBuilder;

/**
 * Date: 11-3-30 Time: 下午4:17
 */
@Controller
public class SuperfishMenuDemoController
        extends MultiActionController {

    @Inject
    private MainMenu mainMenu;

    @RequestMapping("sfmenuDemo.htm")
    public ModelAndView sfmenuDemo(HttpServletRequest request, HttpServletResponse response) {

        String menuHtml = SuperFishMenuBuilder.buildMenubar(mainMenu);

        ModelAndView view = new ModelAndView();
        view.addObject("html", menuHtml);
        return view;
    }

}
