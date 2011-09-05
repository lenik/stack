package com.bee32.sem.frame.web;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class ViewConfig
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    int pageSize = 10;
    String pageSizeTemplate = "5,10,15";
    String allowTypes = "/(\\.|\\/)(gif|jpe?g|png|doc|docx|xsl|xslx|xls)$/";

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

    public String getAllowTypes() {
        return allowTypes;
    }

    public void setAllowTypes(String allowTypes) {
        this.allowTypes = allowTypes;
    }

}
