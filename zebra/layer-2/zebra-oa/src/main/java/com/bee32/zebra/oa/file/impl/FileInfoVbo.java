package com.bee32.zebra.oa.file.impl;

import java.io.IOException;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlATag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.hbin.UploadDialog;
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

        FileInfo fileInfo = ref.get();
        if (fileInfo.getId() != null) {
            IHtmlTag headCol1 = ctx.getTag(ID.headCol1);
            HtmlDivTag filecmds = headCol1.div().class_("zu-links");
            filecmds.p().text("云端文件:");
            HtmlATag uploadLink = filecmds.a().href("javascript: showUploadDialog()");
            uploadLink.iText(FA_UPLOAD, "fa icon").text("上传");
            // uploadLink.iText(FA_ERASER, "fa icon").text("删除");
            // HtmlUlTag ul = filecmds.ul();
        }
    }

    @Override
    protected IHtmlTag extras(IHtmlViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref, IOptions options)
            throws ViewBuilderException, IOException {
        super.extras(ctx, out, ref, options);
        new UploadDialog(out, "uploadDialog");
        return out;
    }

}
