package com.bee32.sem.frame.action;

import org.apache.commons.collections15.Closure;
import org.zkoss.zk.ui.event.Event;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.servlet.context.ContextLocation;

public interface IAction
        extends IComponent {

    boolean isEnabled();

    ContextLocation getTargetLocation();

    Closure<Event> getZkCallback();

}
