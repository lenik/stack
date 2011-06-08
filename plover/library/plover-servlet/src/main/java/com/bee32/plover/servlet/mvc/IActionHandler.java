package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.util.IPriority;

public interface IActionHandler
        extends IPriority {

    String getPrefix();

    void setPrefix(String prefix);

    String getName();

    ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception;

}
