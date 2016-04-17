package com.bee32.zebra.oa.hr.impl;

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

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class EmployeeIndex_htm
        extends SlimIndex_htm<EmployeeIndex, Employee, EmployeeMask> {

    public EmployeeIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeIndex.class);
        indexFields.parse("i*sa", "person", "position", "title", "education", "workYears", "baseSalary");
    }

    @Override
    protected EmployeeMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        EmployeeMask mask = MaskBuilder.fromRequest(new EmployeeMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<EmployeeIndex> ref)
            throws ViewBuilderException, IOException {
        EmployeeMapper mapper = ctx.query(EmployeeMapper.class);
        EmployeeMask mask = ctx.query(EmployeeMask.class);
        List<Employee> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Employee o : list) {
            HtmlTr tr = tbody.tr();
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
        itab.buildViewEnd(tbody);
        return list;
    }

}
