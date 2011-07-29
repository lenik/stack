package com.bee32.sem.people.web;

import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class ModifyPasswordBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private String oldPass;
    private String newPass;
    private String confirmPass;
    private String challenge = "C" + new Random().nextInt();

	public String getOldPass() {
		return oldPass;
	}
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}



	public UserDto getCurrentUser() {
		User u = (User) SessionLoginInfo.getUser();
		User user = serviceFor(User.class).getOrFail(u.getId());
		UserDto cu = DTOs.marshal(UserDto.class, user);

		return cu;
	}


	public void modify() {
		FacesMessage msg = null;

		if(!newPass.equals(confirmPass)) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "修改密码错误", "新密码和新密码确认不匹配");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		User u = (User) SessionLoginInfo.getUser();
		List<UserPassword> plist = serviceFor(UserPassword.class).list(
				LoginCriteria.forUser(u));
		if(plist.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "修改密码错误", "密码不存在");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			String p1 = plist.get(0).getPasswd();

			if(p1.equals(oldPass)) {
				//用户输入的旧密码正确
				plist.get(0).setPasswd(newPass);
				serviceFor(UserPassword.class).saveOrUpdate(plist.get(0));

				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "修改密码成功", "修改密码成功");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				//旧密码不正确
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "修改密码错误", "旧密码输入错误");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

}
