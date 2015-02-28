package com.bee32.zebra.oa.file.impl;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class FileInfoVbo
        extends SlimForm_htm<FileInfo> {

    public FileInfoVbo() {
        super(FileInfo.class);
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("codeName");
        excludes.add("dirName");
        excludes.add("baseName");
    }

    @Override
    protected void setUpFrame(IHttpViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref, IOptions options)
            throws ViewBuilderException, IOException {
        super.setUpFrame(ctx, out, ref, options);

        FileInfo fileInfo = ref.get();
        boolean canUpload = fileInfo.getId() != null;
        canUpload = true;

        if (canUpload) {
            IHtmlTag headCol1 = ctx.getHtmlDoc().getElementById(ID.headCol1);

            HtmlDivTag filecmds = headCol1.div().class_("zu-links");
            // HtmlATag uploadLink = filecmds.a().href("javascript: showUploadDialog()");
            // uploadLink.iText(FA_UPLOAD, "fa icon").text("上传");
            HtmlInputTag fileupload = filecmds.span().class_("btn btn-link fileinput-button")//
                    .iText(FA_PLUS_CIRCLE, "fa").text("上传...")//
                    .input().id("fileupload").type("file").name("files[]");
            fileupload.attr("data-url", _webApp_ + "file/upload");

            HtmlDivTag progress = filecmds.div().class_("fileupload-progress fade");
            HtmlDivTag prog = progress.div().class_("progress progress-success progress-triped active") //
                    .attr("role", "progressbar").attr("aria-valuemin", 0).attr("aria-valuemax", 100);
            prog.div().class_("bar").style("width:0%");

            HtmlDivTag alert = out.div().id("alert-done").class_("alert alert-success").style("display: none");
            alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
            alert.iText(FA_CHECK_CIRCLE, "fa");
            alert.bText("[成功]").text("上传成功");
            alert.hr();
            alert.div().class_("small message").text("Succeeded.");
            alert.div().class_("small alert-warning").iText(FA_EXCLAMATION_TRIANGLE, "fa")
                    .text("在您提交这个更改以前，上传的文件并不会自动保存。");
        }
    }

    @Override
    protected Object persist(boolean create, IHttpViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref)
            throws Exception {
        FileInfo fileInfo = ref.get();

        String incoming = ctx.getRequest().getParameter("incoming");
        if (!Nullables.isEmpty(incoming)) {
            FileManager manager = FileManager.getInstance();
            File incomingFile = new File(manager.incomingDir, incoming);
            if (!incomingFile.exists())
                throw new IllegalStateException("incoming file isn't existed: " + incomingFile);

            // mv OLD OLD.bak
            String oldPath = fileInfo.getPath();
            File oldFile = null;
            File oldFileBak = null;
            if (oldPath != null) {
                oldFile = new File(manager.startDir, oldPath);
                if (oldFile.exists()) {
                    oldFileBak = new File(manager.startDir, oldPath + ".bak");
                    if (!oldFile.renameTo(oldFileBak))
                        throw new IOException(String.format("Can't rename file %s to %s.", oldFile, oldFileBak));
                }
            }

            // mv NEW <old-dir>/incoming
            String newDirName = fileInfo.getDirName();
            if (newDirName == null)
                newDirName = "incoming";

            File newFileDir = new File(manager.startDir, newDirName);
            newFileDir.mkdirs();
            File newFile = new File(newFileDir, incoming);
            if (!incomingFile.renameTo(newFile)) {
                if (oldFileBak != null) {
                    if (!oldFileBak.renameTo(oldFile))
                        throw new IOException(String.format("Can't restore file %s to %s.", oldFileBak, oldFile));
                }
                throw new IOException(String.format("Can't rename file %s to %s.", incomingFile, newFile));
            }

            if (oldFileBak != null)
                oldFileBak.delete();

            fileInfo.setDirName(newDirName);
            fileInfo.setBaseName(incoming);

            if (Nullables.isEmpty(fileInfo.getLabel()))
                fileInfo.setLabel(incoming);
            fileInfo.setSize(newFile.length());
        }

        return super.persist(create, ctx, out, ref);
    }

    @Override
    protected void endForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> ref, IOptions options)
            throws ViewBuilderException, IOException {
        out.input().type("hidden").id("incoming").name("incoming");
        super.endForm(ctx, out, ref, options);
    }

    @Override
    protected IHtmlTag extras(IHttpViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref, IOptions options)
            throws ViewBuilderException, IOException {
        super.extras(ctx, out, ref, options);

        // out.script().javascriptSrc("tmpl.min.js");
        // out.script().javascriptSrc("/js/jquery-file-upload/js/jquery.fileupload-ui.js");
        // out.script().javascriptSrc("/js/jquery-file-upload/js/jquery.fileupload-process.js");

        // new UploadDialog(out, "uploadDialog");
        return out;
    }

}
