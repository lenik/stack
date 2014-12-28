package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
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
        formStruct = new Employee().getFormStruct();
        insertIndexFields("id", "label", "description", "creationTime", "lastModified");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<EmployeeManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        EmployeeManager manager = ref.get();
        EmployeeMapper mapper = manager.getMapper();
        List<Employee> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Employee o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
