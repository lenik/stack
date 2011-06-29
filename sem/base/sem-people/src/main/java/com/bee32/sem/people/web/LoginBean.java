package com.bee32.sem.people.web;

import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class LoginBean extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String challenge = "" + new Random().nextInt();

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



	public String login() {
		User user = serviceFor(User.class).get(username);
		if(user == null) {
			return "login.jsf";
		}
		List<UserPassword> plist = serviceFor(UserPassword.class).list(
				LoginCriteria.userOf(user)
				);
		if(plist.isEmpty()) {
			return "login.jsf";
		} else {
			String p1 = plist.get(0).getPasswd();
			String _p2 = challenge + p1 + challenge;
			String p2 = DigestUtils.shaHex(_p2);

			if(p2.equals(password)) {
				return "one.jsf";
			} else {
				return "login.jsf";
			}
		}
	}
}
