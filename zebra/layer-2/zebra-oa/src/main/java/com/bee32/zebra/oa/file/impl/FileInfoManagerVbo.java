package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.security.User;

public class FileInfoManagerVbo
        extends Zc3Template_CEM<FileInfoManager, FileInfo> {

    public FileInfoManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FileInfoManager.class);
        formStruct = new FileInfo().getFormStruct();
        insertIndexFields("op", "label", "description", "value", "org.label", "person.label");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FileInfoManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        FileInfoManager manager = ref.get();
        FileInfoMapper mapper = manager.getMapper();
        List<FileInfo> list = mapper.all();

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (FileInfo o : list) {
            User op = o.getOp();

            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(op == null ? "" : op.getFullName()).align("center");
            tr.td().b().text(o.getLabel()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getDescription(), 50)).class_("small").style("max-width: 30em");
            tr.td().text(o.getValue());
            stdcols1(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
