package com.tinylily.model.base.security.impl;

import java.io.IOException;
import java.util.List;

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
import com.tinylily.model.base.security.Group;

public class GroupManagerVbo
        extends Zc3Template_CEM<GroupManager, Group> {

    public GroupManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(GroupManager.class);
        formStruct = new Group().getFormStruct();
        insertIndexFields("codeName", "label", "description", "users");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<GroupManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ctx = super.buildHtmlView(ctx, ref, options);

        PageStruct p = new PageStruct(ctx);

        GroupManager manager = ref.get();
        GroupMapper mapper = manager.getMapper();
        List<Group> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Group o : list) {

            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getCodeName());
            stdcolsLD(tr, o);
            tr.td().text(labels(o.getUsers()));
            stdcols1(tr, o);
        }

//        dumpFullData(p.extradata, list);

        return ctx;
    }

}
