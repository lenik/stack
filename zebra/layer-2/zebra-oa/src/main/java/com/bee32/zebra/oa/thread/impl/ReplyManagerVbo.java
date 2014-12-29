package com.bee32.zebra.oa.thread.impl;

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

import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.security.User;

public class ReplyManagerVbo
        extends Zc3Template_CEM<ReplyManager, Reply> {

    public ReplyManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(ReplyManager.class);
        formStruct = new Reply(null, null).getFormStruct();
        insertIndexFields("i*sa", "op", "subject", "text");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<ReplyManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        ReplyManager manager = ref.get();
        ReplyMapper mapper = manager.getMapper();
        List<Reply> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Reply o : list) {
            User op = o.getOp();
            // Topic topic = o.getTopic();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(op == null ? "" : op.getFullName());
            tr.td().b().text(o.getSubject()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getText(), 50)).class_("small").style("max-width: 30em");
            cocols("sa", tr, o);
        }

        return ctx;
    }

}
