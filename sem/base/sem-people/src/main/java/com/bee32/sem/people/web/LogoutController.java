package com.bee32.sem.people.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.ajax.SuccessOrFailMessage;

@Controller
@Lazy
public class LogoutController
        extends MultiActionController {

    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        final HttpSession session = req.getSession();

        new SuccessOrFailMessage("SUCCESS") {

            @Override
            protected String eval()
                    throws Exception {
                LoginInfo.getInstance(session).destroy();
                return null;
            }
        }.jsonDump(res);

    }

}
