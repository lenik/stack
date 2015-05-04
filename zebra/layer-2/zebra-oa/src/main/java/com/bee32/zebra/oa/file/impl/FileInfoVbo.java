package com.bee32.zebra.oa.file.impl;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlATag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlOlTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.hbin.UploadFileDialog;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class FileInfoVbo
        extends SlimForm_htm<FileInfo>
        implements IZebraSiteAnchors {

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
        String baseName = fileInfo.getBaseName();

        IHtmlTag headCol1 = ctx.getHtmlDoc().getElementById(ID.headCol1);
        HtmlDivTag filecmds = headCol1.div().class_("zu-links");

        HtmlATag uploadLink = filecmds.a().href("javascript: uploadDialog.open()");
        uploadLink.iText(FA_UPLOAD, "fa").text(baseName == null ? "上传..." : "重新上传…");
        UploadFileDialog dialog = new UploadFileDialog(out, "uploadDialog");
        dialog.attr("data-forform", "form1");
        dialog.attr("data-bind", "#form1 .incoming, #form1 [name=label]");
        dialog.build();
    }

    @Override
    protected Object persist(boolean create, IHttpViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref)
            throws Exception {
        FileInfo fileInfo = ref.get();

        String incomingName = ctx.getRequest().getParameter("incoming");
        if (!Nullables.isEmpty(incomingName)) {
            FileManager manager = FileManager.forCurrentRequest();
            File incomingFile = new File(manager.getDir(UploadHandler.class), incomingName);
            if (!incomingFile.exists())
                throw new IllegalStateException("incoming file isn't existed: " + incomingFile);

            // mv OLD OLD.bak
            String oldPath = fileInfo.getPath();
            File oldFile = null;
            File oldFileBak = null;
            if (oldPath != null) {
                oldFile = manager.getFile("tree", oldPath);
                if (oldFile.exists()) {
                    oldFileBak = manager.getFile("tree", oldPath + ".bak");
                    if (!oldFile.renameTo(oldFileBak))
                        throw new IOException(String.format("Can't rename file %s to %s.", oldFile, oldFileBak));
                }
            }

            // mv NEW <old-dir>/incoming
            String newDirName = fileInfo.getDirName();
            if (newDirName == null)
                newDirName = "incoming";

            File newFileDir = manager.getFile("tree", newDirName);
            newFileDir.mkdirs();
            File newFile = new File(newFileDir, incomingName);
            if (!incomingFile.equals(newFile))
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
            fileInfo.setBaseName(incomingName);

            if (Nullables.isEmpty(fileInfo.getLabel()))
                fileInfo.setLabel(incomingName);
            fileInfo.setSize(newFile.length());
        }

        return super.persist(create, ctx, out, ref);
    }

    @Override
    protected void errorDiag(IHtmlTag out, Throwable e) {
        super.errorDiag(out, e);
        out.text("下面的信息有助于您找到出错的原因：");
        HtmlOlTag ol = out.ol();
        ol.li().text("相同的文件是否已经存在?");
        ol.li().text("文件名是否含有特殊的符号?");
    }

    @Override
    protected IHtmlTag beforeForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<FileInfo> ref, IOptions options)
            throws ViewBuilderException, IOException {
        super.beforeForm(ctx, out, ref, options);

        FileInfo fileInfo = ref.get();
        String baseName = fileInfo.getBaseName();

        if (baseName != null) {
            SectionDiv section = new SectionDiv(out, "uploaded-file", "云端文件", FA_CLOUD);
            new GetFilePanel(section.contentDiv, fileInfo).build();
        }

        return out;
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
