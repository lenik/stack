package com.bee32.sem.frame.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.sem.frame.builtins.MainMenu;
import com.bee32.sem.frame.menu.SuperFishMenuBuilder;

/**
 * Date: 11-3-30 Time: 下午4:17
 */
@Controller
public class SuperfishMenuController
        extends MultiActionController {

    @Inject
    private MainMenu mainMenu;

    @RequestMapping("sfmenuHtml.htm")
    public void sfmenuHtml(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(SuperFishMenuBuilder.buildMenubar(mainMenu));

        } catch (IOException ex1) {
            ex1.printStackTrace();
        } finally {
            out.close();
        }
    }

}
