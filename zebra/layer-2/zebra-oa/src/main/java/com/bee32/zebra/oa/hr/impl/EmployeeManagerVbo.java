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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class EmployeeManagerVbo
        extends Zc3Template_CEM<EmployeeManager, Employee> {

    public EmployeeManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeManager.class);
        formDef = new Employee().getFormDef();
        insertIndexFields("i*sa", "id", "label", "description", "creationTime", "lastModified");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<EmployeeManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        EmployeeMapper mapper = ctx.query(EmployeeMapper.class);
        List<Employee> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(page.mainCol, "list");
        for (Employee o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
