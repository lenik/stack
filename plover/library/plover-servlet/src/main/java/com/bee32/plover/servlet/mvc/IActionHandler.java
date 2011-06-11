package com.bee32.plover.servlet.mvc;

import com.bee32.plover.arch.util.IPriority;

public interface IActionHandler
        extends IPriority {

//    String getPrefix();
//
//    void setPrefix(String prefix);

    String getName();

    ActionResult handleRequest(ActionRequest req, ActionResult result)
            throws Exception;

}
