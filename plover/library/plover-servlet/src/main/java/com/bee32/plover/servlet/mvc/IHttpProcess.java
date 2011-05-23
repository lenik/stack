package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.servlet.context.Location;

public interface IHttpProcess {

    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    Object getRequestModel();

    void go(String viewId);

    void go(Location location);

    void go(Object targetView);

    HttpTargetViewType getTargetViewType();

    Object getTargetView();

}
