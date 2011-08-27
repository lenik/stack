package com.bee32.icsf.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.ox1.principal.User;

@Service
@Lazy
public class LoginManager
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(LoginManager.class);

    List<ILoginListener> listeners;

    public LoginManager() {
        listeners = new ArrayList<ILoginListener>();
    }

    @Override
    public void setApplicationContext(ApplicationContext appctx)
            throws BeansException {
        try {
            for (Class<? extends ILoginListener> listenerClass : ServicePrototypeLoader.load(ILoginListener.class)) {
                for (ILoginListener listener : appctx.getBeansOfType(listenerClass).values()) {
                    logger.debug("Register login-listener: " + listener);
                    listeners.add(listener);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
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

}
