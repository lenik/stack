package com.bee32.icsf.login;

public class InitSessionLoginAdapter
        extends LoginAdapter {

    @Override
    public void logIn(LoginEvent event) {
        LoginInfo loginInfo = LoginInfo.getInstance();
        loginInfo.setUser(event.user);
    }

    @Override
    public void logOut(LoginEvent event) {
        LoginInfo loginInfo = LoginInfo.getInstance();
        loginInfo.destroy();
    }

}
