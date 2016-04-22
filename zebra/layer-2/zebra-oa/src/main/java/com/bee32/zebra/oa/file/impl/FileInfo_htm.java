package com.bee32.zebra.oa.file.impl;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlOl;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.hbin.SectionDiv_htm1;
import com.bee32.zebra.tk.hbin.UploadFileDialog_htm;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class FileInfo_htm
        extends SlimForm_htm<FileInfo>
        implements IZebraSiteAnchors {

    public FileInfo_htm() {
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
    protected void setUpFrame(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FileInfo> ref)
            throws ViewBuilderException, IOException {
        super.setUpFrame(ctx, out, ref);

        UploadFileDialog_htm dialog = new UploadFileDialog_htm();
        dialog.dataForform = "form1";
        dialog.dataBind = "#form1 .incoming, #form1 [name=label]";
        dialog.build(out, "uploadDialog");
    }

    @Override
    protected Object persist(boolean create, IHtmlViewContext ctx, IHtmlOut out, IUiRef<FileInfo> ref)
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
    protected void errorDiag(IHtmlOut out, Throwable e) {
        super.errorDiag(out, e);
        out.text("下面的信息有助于您找到出错的原因：");
        HtmlOl ol = out.ol();
        ol.li().text("相同的文件是否已经存在?");
        ol.li().text("文件名是否含有特殊的符号?");
    }

    @Override
    protected IHtmlOut beforeForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FileInfo> ref)
            throws ViewBuilderException, IOException {
        super.beforeForm(ctx, out, ref);

        FileInfo fileInfo = ref.get();
        String baseName = fileInfo.getBaseName();

        if (baseName != null) {
            IHtmlOut sect = new SectionDiv_htm1("云端文件", FA_CLOUD).build(out, "uploaded-file");
            new GetFilePanel(fileInfo).build(sect);
        }

        return out;
    }

    @Override
    protected void endForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> ref)
            throws ViewBuilderException, IOException {
        out.input().type("hidden").id("incoming").name("incoming").attr("data-role", "uploaded-name");
        super.endForm(ctx, out, ref);
    }

    @Override
    protected IHtmlOut extras(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FileInfo> ref)
            throws ViewBuilderException, IOException {
        super.extras(ctx, out, ref);

        // out.script().javascriptSrc("tmpl.min.js");
        // out.script().javascriptSrc("/js/jquery-file-upload/js/jquery.fileupload-ui.js");
        // out.script().javascriptSrc("/js/jquery-file-upload/js/jquery.fileupload-process.js");

        // new UploadDialog(out, "uploadDialog");
        return out;
    }

}
