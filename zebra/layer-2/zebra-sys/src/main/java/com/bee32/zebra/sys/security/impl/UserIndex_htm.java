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
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.base.security.impl.UserMapper;
import net.bodz.lily.model.base.security.impl.UserMask;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
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
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<UserIndex> ref)
            throws ViewBuilderException, IOException {
        UserMapper mapper = ctx.query(UserMapper.class);
        UserMask mask = ctx.query(UserMask.class);
        List<User> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (User o : list) {
            long lastLoginTime = o.getLastLoginTime();

            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getLoginName());
            itab.cocols("u", tr, o);
            ref(tr.td(), o.getPrimaryGroup());
            tr.td().text(fn2.labels(o.getGroups()));
            tr.td().text(o.getEmail()).style(o.isEmailValidated() ? "" : "color: gray");
            tr.td().text(lastLoginTime == 0 ? null : fmt.formatDate(o.getLastLoginTime()));
            tr.td().text(o.getLastLoginIP());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
