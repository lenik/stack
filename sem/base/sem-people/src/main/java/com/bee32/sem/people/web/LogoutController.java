package com.bee32.sem.people.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.plover.ajax.SuccessOrFailMessage;

@Controller
@Lazy
public class LogoutController extends MultiActionController {

	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		new SuccessOrFailMessage("SUCCESS") {

			@Override
			protected String eval() throws Exception {
				SessionLoginInfo.setUser(null);

				return null;
			}
		}.jsonDump(res);

	}
}
