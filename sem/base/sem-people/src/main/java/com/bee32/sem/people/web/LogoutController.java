package com.bee32.sem.people.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.icsf.login.LoginInfo;
import com.bee32.icsf.login.LoginManager;
import com.bee32.plover.ajax.SuccessOrFailMessage;
import com.bee32.plover.ox1.principal.User;

@Controller
@Lazy
public class LogoutController
        extends MultiActionController {

    @Inject
    LoginManager loginManager;

    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        new SuccessOrFailMessage("SUCCESS") {

            @Override
            protected String eval()
                    throws Exception {
                User userOpt = LoginInfo.getInstance().getInternalUserOpt();

                try {
                    loginManager.logOut(userOpt);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Failed to logout: " + e.getMessage();
                }

                return null;
            }
        }.jsonDump(res);

    }

}
