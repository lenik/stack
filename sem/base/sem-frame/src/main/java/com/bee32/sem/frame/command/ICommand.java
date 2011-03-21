package com.bee32.sem.frame.command;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.servlet.context.ContextLocation;

public interface ICommand
        extends IComponent {

    ContextLocation getLocation();

}
