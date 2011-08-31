package com.bee32.sem.frame.web;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class ViewConfig
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    int pageSize = 10;
    String pageSizeTemplate = "5,10,15";

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageSizeTemplate() {
        return pageSizeTemplate;
    }

    public void setPageSizeTemplate(String pageSizeTemplate) {
        this.pageSizeTemplate = pageSizeTemplate;
    }

}
