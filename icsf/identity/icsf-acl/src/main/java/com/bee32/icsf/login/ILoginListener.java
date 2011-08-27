package com.bee32.icsf.login;

public interface ILoginListener {

    void logIn(LoginEvent event);

    void logOut(LoginEvent event);

    @Deprecated
    void refresh(LoginEvent event);

}
