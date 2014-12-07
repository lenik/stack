package com.bee32.zebra.oa.cloudfs.impl;

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

import com.bee32.zebra.oa.cloudfs.CloudFile;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.security.User;

public class CloudFileManagerVbo
        extends Zc3Template_CEM<CloudFileManager, CloudFile> {

    public CloudFileManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(CloudFileManager.class);
        formStruct = new CloudFile().getFormStruct();
        setIndexFields("id", "party.label", "label", "description", "op" //
                , "creationDate", "lastModified" //
        // , "owner.label", "ownerGroup.label"
        );
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<CloudFileManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        CloudFileManager manager = ref.get();
        CloudFileMapper mapper = manager.getMapper();
        List<CloudFile> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (CloudFile o : list) {
            User op = o.getOp();

            HtmlTrTag tr = indexTable.tbody.tr();
            tr.td().text(o.getId()).class_("col-id");
            tr.td().text(o.getParty().getLabel());
            tr.td().b().text(o.getLabel()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getDescription(), 50)).class_("small").style("max-width: 30em");
            tr.td().text(op == null ? "" : op.getFullName()).align("center");
            stdcols(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
