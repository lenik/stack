package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
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

public class FileInfoManagerVbo
        extends Zc3Template_CEM<FileInfoManager, FileInfo> {

    public FileInfoManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FileInfoManager.class);

        formStruct = new FileInfo().getFormStruct();
        insertIndexFields("i*sa", "op", "label", "description", "size", "org", "person", "value", "tags", "downloads",
                "expireDate");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FileInfoManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        FileInfoManager manager = ref.get();
        FileInfoMapper mapper = manager.getMapper();
        List<FileInfo> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (FileInfo o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);

            ref(tr.td(), o.getOp()).align("center");
            cocols("u", tr, o);
            tr.td().text(o.getSize()).class_("small");
            ref(tr.td(), o.getOrg()).class_("small");
            ref(tr.td(), o.getPerson()).class_("small");
            tr.td().text(o.getValue()).style("font-weight: bold");
            tr.td().text(o.getTags()).class_("small");
            tr.td().text(o.getDownloads());
            tr.td().text(fn.formatDate(o.getExpireDate()));

            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
