package com.tinylily.model.base.security.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.security.User;

public class UserManagerVbo
        extends Zc3Template_CEM<UserManager, User> {

    public UserManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(UserManager.class);
        formStruct = new User().getFormStruct();
        insertIndexFields("loginName", "label", "description", "primaryGroup", "groups", "email", "lastLoginTime",
                "lastLoginIP");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<UserManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ctx = super.buildHtmlView(ctx, ref, options);

        PageStruct p = new PageStruct(ctx);

        UserManager manager = ref.get();
        UserMapper mapper = manager.getMapper();
        List<User> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (User o : list) {
            long lastLoginTime = o.getLastLoginTime();

            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getLoginName());
            stdcolsLD(tr, o);
            ref(tr.td(), o.getPrimaryGroup());
            tr.td().text(labels(o.getGroups()));
            tr.td().text(o.getEmail()).style(o.isEmailValidated() ? "" : "color: gray");
            tr.td().text(lastLoginTime == 0 ? null : Dates.YYYY_MM_DD.format(o.getLastLoginTime()));
            tr.td().text(o.getLastLoginIP());
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }
}
