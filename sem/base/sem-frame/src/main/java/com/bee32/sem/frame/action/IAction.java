package com.bee32.sem.frame.action;

import javax.faces.event.ActionListener;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.rtx.location.ILocationContext;

public interface IAction
        extends IComponent {

    boolean isEnabled();

    ILocationContext getTargetLocation();

    // Closure<org.zkoss.zk.ui.event.Event> getZkCallback();

    ActionListener getActionListener();

}
