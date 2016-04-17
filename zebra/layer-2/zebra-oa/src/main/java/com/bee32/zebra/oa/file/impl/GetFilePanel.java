package com.bee32.zebra.oa.file.impl;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlA;
import net.bodz.bas.html.io.tag.HtmlTable;
import net.bodz.bas.html.io.tag.HtmlTd;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.hbin.PathBreadcrumb;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class GetFilePanel
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public String class_;

    public String schema;
    public String dirName;
    public String baseName;
    public String description;
    public String href;
    public boolean breadcrumb = false;

    public GetFilePanel() {
    }

    public GetFilePanel(FileInfo fileInfo) {
        schema = "tree";
        dirName = fileInfo.getDirName();
        baseName = fileInfo.getBaseName();
        description = fileInfo.getDescription();
    }

    public void build(IHtmlOut out) {
        HtmlTable table = out.table();
        if (class_ != null)
            table.class_(class_);

        HtmlTr tr = table.tr();
        HtmlTd left = tr.td().valign("top").style("width: 4em");
        left.div().align("center").i().class_("fa fa-2x").text(FA_FILE_O);
        left.hr();

        String href = this.href;
        if (href == null)
            href = _webApp_ + "files/" + schema + "/" + dirName + "/" + baseName;

        HtmlA downloadLink = left.span().a().href(href + "?mode=attachment");
        downloadLink.i().class_("fa").text(FA_DOWNLOAD);
        downloadLink.text("下载");

        HtmlTd right = tr.td().valign("top");
        HtmlA fileLink = right.div().a().href("_blank", href);
        fileLink.i().class_("fa").text(FA_EXTERNAL_LINK);
        fileLink.b().text(baseName);

        if (breadcrumb) {
            if (dirName != null)
                new PathBreadcrumb().build(right.div(), dirName);
        } else {
            right.div().text(dirName);
        }

        if (description != null)
            right.div().text(description);

        // right.hr();
        // right.div().text("下载次数：$downloads");
    }

}
