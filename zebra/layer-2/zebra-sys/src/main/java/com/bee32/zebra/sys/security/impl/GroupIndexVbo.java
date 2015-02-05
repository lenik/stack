package com.bee32.zebra.sys.security.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.tinylily.model.base.security.Group;
import com.tinylily.model.base.security.impl.GroupCriteria;
import com.tinylily.model.base.security.impl.GroupMapper;

public class GroupIndexVbo
        extends SlimIndex_htm<GroupIndex, Group, GroupCriteria> {

    public GroupIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(GroupIndex.class);
        indexFields.parse("i*sa", "codeName", "label", "description", "users");
    }

    @Override
    protected GroupCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        GroupCriteria criteria = fn.criteriaFromRequest(new GroupCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Group> a, IUiRef<GroupIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        GroupMapper mapper = ctx.query(GroupMapper.class);
        GroupCriteria criteria = ctx.query(GroupCriteria.class);
        List<Group> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Group o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getCodeName());
                itab.cocols("u", tr, o);
                tr.td().text(fn.labels(o.getUsers()));
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
