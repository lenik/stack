package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class EmployeeIndexVbo
        extends Zc3Template_CEM<EmployeeIndex, Employee> {

    public EmployeeIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeIndex.class);
        indexFields.parse("i*sa", "id", "label", "description", "creationTime", "lastModified");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Employee> a, IUiRef<EmployeeIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        EmployeeMapper mapper = ctx.query(EmployeeMapper.class);
        List<Employee> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Employee o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
