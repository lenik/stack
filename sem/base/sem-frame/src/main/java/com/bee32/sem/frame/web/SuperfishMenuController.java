package com.bee32.sem.frame.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuLoader;
import com.bee32.sem.frame.menu.SuperfishMenuBuilder;

@Controller
@Lazy
public class SuperfishMenuController {

    @Inject
    MenuLoader menuLoader;

    @RequestMapping("sfmenuHtml.do")
    public void sfmenuHtml(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            out.println(new SuperfishMenuBuilder(SEMFrameMenu.MAIN, request));

        } catch (IOException ex1) {
            ex1.printStackTrace();
        } finally {
            out.close();
        }
    }

}
