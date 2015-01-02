package com.tinylily.model.base.security.impl;

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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.security.Group;

public class GroupManagerVbo
        extends Zc3Template_CEM<GroupManager, Group> {

    public GroupManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(GroupManager.class);
        insertIndexFields("i*sa", "codeName", "label", "description", "users");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<GroupManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        GroupMapper mapper = ctx.query(GroupMapper.class);
        List<Group> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(page.mainCol, "list");
        for (Group o : list) {

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getCodeName());
            cocols("u", tr, o);
            tr.td().text(fn.labels(o.getUsers()));
            cocols("sa", tr, o);
        }

//        dumpFullData(p.extradata, list);
    }

}
