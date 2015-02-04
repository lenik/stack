package com.bee32.zebra.oa.file.impl;

import java.io.IOException;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlATag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlFormTag;
import net.bodz.bas.html.dom.tag.HtmlUlTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class FileInfoVbo
        extends SlimForm_htm<FileInfo> {

    public FileInfoVbo() {
        super(FileInfo.class);
    }

    @Override
    protected void setUpFrame(IHtmlViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref, IOptions options)
            throws ViewBuilderException, IOException {
        super.setUpFrame(ctx, out, ref, options);

        IHtmlTag headCol1 = ctx.getTag(ID.headCol1);

        HtmlDivTag filecmds = headCol1.div().class_("zu-links");
        filecmds.div().text("云端文件:");
        HtmlUlTag ul = filecmds.ul();
        HtmlATag uploadLink = filecmds.a().href("");
        uploadLink.spanText("fa icon", FA_UPLOAD, "上传");
    }

    @Override
    protected HtmlFormTag beginForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<?> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return super.beginForm(ctx, out, ref, options);
    }

}
