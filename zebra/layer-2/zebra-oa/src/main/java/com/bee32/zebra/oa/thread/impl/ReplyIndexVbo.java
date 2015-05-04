package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class ReplyIndexVbo
        extends SlimIndex_htm<ReplyIndex, Reply, ReplyCriteria> {

    public ReplyIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(ReplyIndex.class);
        indexFields.parse("i*sa", "op", "subject", "text");
    }

    @Override
    protected ReplyCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ReplyCriteria criteria = CriteriaBuilder.fromRequest(new ReplyCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<Reply> a, IUiRef<ReplyIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ReplyMapper mapper = ctx.query(ReplyMapper.class);
        ReplyCriteria criteria = ctx.query(ReplyCriteria.class);
        List<Reply> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Reply o : list) {
                User op = o.getOp();
                // Topic topic = o.getTopic();

                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(op == null ? "" : op.getFullName());
                tr.td().b().text(o.getSubject()).class_("small").style("max-width: 20em");
                tr.td().text(Strings.ellipsis(o.getText(), 50)).class_("small").style("max-width: 30em");
                itab.cocols("sa", tr, o);
            }
    }

}
