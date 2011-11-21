package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletRequestListener;

import com.bee32.plover.arch.util.IPriority;

/**
 * @see ServletRequestListener
 * @see IServletContextListener
 */
public interface IServletRequestListener
        extends ServletRequestListener, IPriority {

}
