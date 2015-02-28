package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class EmployeeIndexVbo
        extends SlimIndex_htm<EmployeeIndex, Employee, EmployeeCriteria> {

    public EmployeeIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeIndex.class);
        indexFields.parse("i*sa", "person", "position", "title", "education", "workYears", "baseSalary");
    }

    @Override
    protected EmployeeCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        EmployeeCriteria criteria = fn.criteriaFromRequest(new EmployeeCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<Employee> a, IUiRef<EmployeeIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        EmployeeMapper mapper = ctx.query(EmployeeMapper.class);
        EmployeeCriteria criteria = ctx.query(EmployeeCriteria.class);
        List<Employee> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Employee o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getPerson());
                tr.td().text(o.getPosition());
                tr.td().text(o.getTitle());
                tr.td().text(o.getEducation());
                // tr.td().text(o.getDuty());
                tr.td().text(o.getWorkYears());
                tr.td().text(o.getBaseSalary());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
