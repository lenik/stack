package com.bee32.sem.frame.action;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.servlet.context.ContextLocation;

public interface IAction
        extends IComponent {

    ContextLocation getTarget();

    boolean isEnabled();

}
