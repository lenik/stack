package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.rtx.location.Location;

public class HttpProcess
        extends HttpServletRequestWrapper
        implements IHttpProcess {

    final HttpServletRequest request;
    final HttpServletResponse response;

    Object requestModel;
    HttpTargetViewType targetViewType;
    Object targetView;

    public HttpProcess(HttpServletRequest request, HttpServletResponse response) {
        super(request);
        if (response == null)
            throw new NullPointerException("response");
        this.request = request;
        this.response = response;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }

    public Object getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(Object requestModel) {
        this.requestModel = requestModel;
    }

    @Override
    public void go(String viewId) {
        targetViewType = HttpTargetViewType.VIEW_ID;
        targetView = viewId;
    }

    @Override
    public void go(Location location) {
        targetViewType = HttpTargetViewType.LOCATION;
        targetView = location;
    }

    @Override
    public void go(Object targetView) {
        targetViewType = HttpTargetViewType.VIEW_OBJECT;
        this.targetView = targetView;
    }

    @Override
    public HttpTargetViewType getTargetViewType() {
        return targetViewType;
    }

    @Override
    public Object getTargetView() {
        return targetView;
    }

}
