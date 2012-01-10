package com.bee32.sem.frame.web;

import com.bee32.plover.web.faces.view.ViewBean;

public class ViewConfig
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    int pageSize = 50;
    int dialogPageSize = 10;

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

    String defaultUpdateTarget = ":mainForm";

    public ViewConfig() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDialogPageSize() {
        return dialogPageSize;
    }

    public void setDialogPageSize(int dialogPageSize) {
        this.dialogPageSize = dialogPageSize;
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

    public String getDefaultUpdateTarget() {
        return defaultUpdateTarget;
    }

    public void setDefaultUpdateTarget(String defaultUpdateTarget) {
        this.defaultUpdateTarget = defaultUpdateTarget;
    }

}
