package com.bee32.sem.frame.web;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.web.faces.utils.ILocationConstants;
import com.bee32.plover.web.faces.utils.LocationVmap;
import com.bee32.plover.web.faces.view.ViewBean;
import com.bee32.sem.frame.SEMFrameModule;

public class ViewConfig
        extends ViewBean
        implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    static LocationVmap homeLocation = WEB_APP.join(SEMFrameModule.PREFIX + "/empty.jsf");

    int pageSize = 20;
    int dialogPageSize = 10;

    String pageSizeTemplate = "10, 20, 50, 100, 200, 500";
    String paginatorTemplate = "" //
            + "{CurrentPageReport}"//
            + " {FirstPageLink}" //
            + " {PreviousPageLink}" //
            + " {PageLinks}" //
            + " {NextPageLink}" //
            + " {LastPageLink}" //
            + " {RowsPerPageDropdown}";

    String allowTypes = "/(\\.|\\/)(gif|jpe?g|png|docx?|xlsx?|pdf|zip|rar)$/";
    long maxFileSize = 10_000_000;

    boolean dynamicDialog = true;
    String defaultUpdateTarget = ":mainForm";
    boolean smallFont = true;

    public ViewConfig() {
    }

    public LocationVmap getHomeLocation() {
        return homeLocation;
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

    public void setPageSizeTemplate(String pageSizeTemplate) {
        this.pageSizeTemplate = pageSizeTemplate;
    }

    public String getPaginatorTemplate() {
        return paginatorTemplate;
    }

    public void setPaginatorTemplate(String paginatorTemplate) {
        this.paginatorTemplate = paginatorTemplate;
    }

    public String getAllowTypes() {
        return allowTypes;
    }

    public void setAllowTypes(String allowTypes) {
        this.allowTypes = allowTypes;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getDefaultUpdateTarget() {
        return defaultUpdateTarget;
    }

    public void setDefaultUpdateTarget(String defaultUpdateTarget) {
        this.defaultUpdateTarget = defaultUpdateTarget;
    }

    public String getToolbarPlacement() {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        String toolbarPlacement = site.getProperty("frame.toolbar");
        if (toolbarPlacement == null)
            toolbarPlacement = "t";
        return toolbarPlacement;
    }

    public boolean isDynamicDialog() {
        return dynamicDialog;
    }

    public void setDynamicDialog(boolean dynamicDialog) {
        this.dynamicDialog = dynamicDialog;
    }

    public boolean isSmallFont() {
        return smallFont;
    }

    public void setSmallFont(boolean smallFont) {
        this.smallFont = smallFont;
    }

}
