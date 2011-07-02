package com.bee32.sem.people.web;

import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class LoginBean extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String challenge = "C" + new Random().nextInt();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}



	public void login(ActionEvent even) {
		FacesMessage msg = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean loggedIn = false;

		SessionLoginInfo.setCurrentUser(null);

		User user = serviceFor(User.class).get(username);
		if(user == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "登录错误", "用户不存在");
		} else {
			List<UserPassword> plist = serviceFor(UserPassword.class).list(
					LoginCriteria.userOf(user));
			if(plist.isEmpty()) {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "登录错误", "密码不存在");
			} else {
				String p1 = plist.get(0).getPasswd();
				String _p2 = challenge + p1 + challenge;
				String p2 = DigestUtils.shaHex(_p2);

				if(p2.equals(password)) {
					SessionLoginInfo.setCurrentUser(user);

					loggedIn = true;
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "登录成功", "登录成功");
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "登录错误", "密码输入错误");
				}
			}
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("loggedIn", loggedIn);
	}
}
