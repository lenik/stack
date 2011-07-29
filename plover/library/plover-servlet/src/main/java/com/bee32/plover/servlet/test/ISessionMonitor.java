package com.bee32.plover.servlet.test;

import javax.servlet.http.HttpSession;

/**
 * SessionMonitorFilter -> check session-init-key -> *.initSession().
 *
 * I.e. When there is no web requests, no session would be initialized.
 */
public interface ISessionMonitor {

    void initSession(HttpSession session);

}
