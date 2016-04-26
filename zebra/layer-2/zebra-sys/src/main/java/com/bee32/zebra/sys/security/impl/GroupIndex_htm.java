package com.bee32.zebra.sys.security.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.security.Group;
import net.bodz.lily.model.base.security.impl.GroupMapper;
import net.bodz.lily.model.base.security.impl.GroupMask;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class GroupIndex_htm
        extends SlimIndex_htm<GroupIndex, Group, GroupMask> {

    public GroupIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(GroupIndex.class);
        indexFields.parse("i*sa", "codeName", "label", "description", "users");
    }

    @Override
    protected GroupMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        GroupMask mask = MaskBuilder.fromRequest(new GroupMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<GroupIndex> ref)
            throws ViewBuilderException, IOException {
        GroupMapper mapper = ctx.query(GroupMapper.class);
        GroupMask mask = ctx.query(GroupMask.class);
        List<Group> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Group o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getCodeName());
            itab.cocols("u", tr, o);
            tr.td().text(fn2.labels(o.getUsers()));
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
