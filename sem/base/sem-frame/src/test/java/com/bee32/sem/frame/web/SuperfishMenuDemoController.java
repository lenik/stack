package com.bee32.sem.frame.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.plover.inject.ControllerTemplate;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuLoader;
import com.bee32.sem.frame.menu.SuperfishMenuBuilder;

@ControllerTemplate
@Lazy
public class SuperfishMenuDemoController
        extends MultiActionController {

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
