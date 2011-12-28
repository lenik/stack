package com.bee32.icsf.login;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;

public class LoginManager {

    static Logger logger = LoggerFactory.getLogger(LoginManager.class);

    List<ILoginListener> listeners;

    public LoginManager() {
        listeners = new ArrayList<ILoginListener>();
        for (ILoginListener listener : ServiceLoader.load(ILoginListener.class)) {
            logger.debug("Register login-listener: " + listener);
            listeners.add(listener);
        }
    }

    public void logIn(User user) {
        LoginEvent event = new LoginEvent(this, user);
        for (ILoginListener listener : listeners)
            listener.logIn(event);
    }

    public void logOut(User user) {
        LoginEvent event = new LoginEvent(this, user);
        for (ILoginListener listener : listeners)
            listener.logOut(event);
    }

    @Deprecated
    public void refresh(User user) {
        LoginEvent event = new LoginEvent(this, user);
        for (ILoginListener listener : listeners)
            listener.refresh(event);
    }

    private static LoginManager instance = new LoginManager();

    public static LoginManager getInstance() {
        return instance;
    }

}
