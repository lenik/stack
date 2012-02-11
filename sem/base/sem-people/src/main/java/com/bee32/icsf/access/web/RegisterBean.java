package com.bee32.icsf.access.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityViewBean;

public class RegisterBean extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private String username;
	private String fullname;
	private String password;
	private String passwordConfirm;

	public String getUsername() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void setUsername(String username) {
		this.username = username;
	}




	public void register(ActionEvent even) {
		FacesMessage msg = null;

		if(!password.equals(passwordConfirm)) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "注册错误", "密码和密码确认不匹配");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		User user = ctx.data.access(User.class).getByName(username);
		if(user != null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "注册错误", "此用户名已经存在");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}


		User newUser = new User(username);
		newUser.setFullName(fullname);
		ctx.data.access(User.class).save(newUser);

		UserPassword pass = new UserPassword(newUser, password);
		ctx.data.access(UserPassword.class).save(pass);


		username = "";
		fullname = "";
		password = "";
		passwordConfirm = "";

		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "注册成功", "注册新用户成功");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
}
