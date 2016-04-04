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
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.base.security.impl.UserMask;
import net.bodz.lily.model.base.security.impl.UserMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class UserIndex_htm
        extends SlimIndex_htm<UserIndex, User, UserMask> {

    public UserIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(UserIndex.class);
        indexFields.parse("i*sa", "loginName", "label", "description", "primaryGroup", "groups", "email",
                "lastLoginTime", "lastLoginIP");
    }

    @Override
    protected UserMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        UserMask mask = MaskBuilder.fromRequest(new UserMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<User> a, IUiRef<UserIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        UserMapper mapper = ctx.query(UserMapper.class);
        UserMask mask = ctx.query(UserMask.class);
        List<User> list = a.noList() ? null : postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (User o : list) {
                long lastLoginTime = o.getLastLoginTime();

                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getLoginName());
                itab.cocols("u", tr, o);
                ref(tr.td(), o.getPrimaryGroup());
                tr.td().text(fn.labels(o.getGroups()));
                tr.td().text(o.getEmail()).style(o.isEmailValidated() ? "" : "color: gray");
                tr.td().text(lastLoginTime == 0 ? null : fmt.formatDate(o.getLastLoginTime()));
                tr.td().text(o.getLastLoginIP());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
