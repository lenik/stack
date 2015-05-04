package com.bee32.zebra.oa.file.impl;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlATag;
import net.bodz.bas.html.dom.tag.HtmlTableTag;
import net.bodz.bas.html.dom.tag.HtmlTdTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.dom.tag._HtmlDivTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.hbin.PathBreadcrumb;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class GetFilePanel
        extends _HtmlDivTag<GetFilePanel>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public HtmlTableTag table;

    String schema;
    String dirName;
    String baseName;
    String description;
    String href;
    boolean breadcrumb = false;

    public GetFilePanel(IHtmlTag parent) {
        super(parent, "div");
        table = table();
    }

    public GetFilePanel(IHtmlTag parent, FileInfo fileInfo) {
        this(parent);
        schema = "tree";
        dirName = fileInfo.getDirName();
        baseName = fileInfo.getBaseName();
        description = fileInfo.getDescription();
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(boolean breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public void build() {
        HtmlTrTag tr = tr();
        HtmlTdTag left = tr.td().valign("top").style("width: 4em");
        left.div().align("center").i().class_("fa fa-2x").text(FA_FILE_O);
        left.hr();

        String href = this.href;
        if (href == null)
            href = _webApp_ + "files/" + schema + "/" + dirName + "/" + baseName;

        HtmlATag downloadLink = left.span().a().href(href + "?mode=attachment");
        downloadLink.i().class_("fa").text(FA_DOWNLOAD);
        downloadLink.text("下载");

        HtmlTdTag right = tr.td().valign("top");
        HtmlATag fileLink = right.div().a().href("_blank", href);
        fileLink.i().class_("fa").text(FA_EXTERNAL_LINK);
        fileLink.b().text(baseName);

        if (breadcrumb) {
            if (dirName != null)
                new PathBreadcrumb(right.div(), dirName);
        } else {
            right.div().text(dirName);
        }

        if (description != null)
            right.div().text(description);

        // right.hr();
        // right.div().text("下载次数：$downloads");
    }

}
