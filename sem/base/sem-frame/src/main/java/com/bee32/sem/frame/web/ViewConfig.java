package com.bee32.sem.frame.web;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class ViewConfig
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    int pageSize = 50;
    final String pageSizeTemplate = "10, 50, 100, 200, 1000";
    final String paginatorTemplate = "" //
            + "{CurrentPageReport}"//
            + " {FirstPageLink}" //
            + " {PreviousPageLink}" //
            + " {PageLinks}" //
            + " {NextPageLink}" //
            + " {LastPageLink}" //
            + " {RowsPerPageDropdown}";

    final String allowTypes = "/(\\.|\\/)(gif|jpe?g|png|docx?|xlsx?|pdf|zip|rar)$/";

    public ViewConfig() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageSizeTemplate() {
        return pageSizeTemplate;
    }

    public String getPaginatorTemplate() {
        return paginatorTemplate;
    }

    public String getAllowTypes() {
        return allowTypes;
    }

}
