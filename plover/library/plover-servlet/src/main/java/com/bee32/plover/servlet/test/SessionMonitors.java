package com.bee32.plover.servlet.test;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.http.HttpSession;

public class SessionMonitors
        implements ISessionMonitor {

    static final List<ISessionMonitor> monitors;

    static {
        monitors = new ArrayList<ISessionMonitor>();
        for (ISessionMonitor monitor : ServiceLoader.load(ISessionMonitor.class))
            monitors.add(monitor);
    }

    @Override
    public void initSession(HttpSession session) {
        for (ISessionMonitor monitor : monitors)
            monitor.initSession(session);
    }

    public static final SessionMonitors INSTANCE = new SessionMonitors();

}
