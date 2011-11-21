package com.bee32.plover.servlet.peripheral;

import javax.servlet.http.HttpSessionListener;

import com.bee32.plover.arch.util.IPriority;

/**
 * NOTICE: Jetty-6 doesn't support http session listener.
 *
 * @see HttpSessionListener
 */
public interface IHttpSessionListener
        extends HttpSessionListener, IPriority {

}
