package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class ReplyIndex_htm
        extends SlimIndex_htm<ReplyIndex, Reply, ReplyMask> {

    public ReplyIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(ReplyIndex.class);
        indexFields.parse("i*sa", "op", "subject", "text");
    }

    @Override
    protected ReplyMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ReplyMask mask = VarMapState.restoreFrom(new ReplyMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<ReplyIndex> ref)
            throws ViewBuilderException, IOException {
        ReplyMapper mapper = ctx.query(ReplyMapper.class);
        ReplyMask mask = ctx.query(ReplyMask.class);
        List<Reply> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Reply o : list) {
            User op = o.getOp();
            // Topic topic = o.getTopic();

            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(op == null ? "" : op.getFullName());
            tr.td().b().text(o.getSubject()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getText(), 50)).class_("small").style("max-width: 30em");
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
