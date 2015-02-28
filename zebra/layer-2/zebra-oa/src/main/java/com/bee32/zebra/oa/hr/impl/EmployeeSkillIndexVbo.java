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

import com.bee32.zebra.oa.hr.EmployeeSkill;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class EmployeeSkillIndexVbo
        extends SlimIndex_htm<EmployeeSkillIndex, EmployeeSkill, EmployeeSkillCriteria> {

    public EmployeeSkillIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeSkillIndex.class);
        indexFields.parse("i*sa", "label", "description");
    }

    @Override
    protected EmployeeSkillCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        EmployeeSkillCriteria criteria = fn.criteriaFromRequest(new EmployeeSkillCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<EmployeeSkill> a, IUiRef<EmployeeSkillIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        EmployeeSkillMapper mapper = ctx.query(EmployeeSkillMapper.class);
        EmployeeSkillCriteria criteria = ctx.query(EmployeeSkillCriteria.class);
        List<EmployeeSkill> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (EmployeeSkill o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
