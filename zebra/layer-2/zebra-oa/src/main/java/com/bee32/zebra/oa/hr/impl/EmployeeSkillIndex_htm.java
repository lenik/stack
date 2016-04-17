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

import com.bee32.zebra.oa.hr.EmployeeSkill;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class EmployeeSkillIndex_htm
        extends SlimIndex_htm<EmployeeSkillIndex, EmployeeSkill, EmployeeSkillMask> {

    public EmployeeSkillIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeSkillIndex.class);
        indexFields.parse("i*sa", "label", "description");
    }

    @Override
    protected EmployeeSkillMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        EmployeeSkillMask mask = MaskBuilder.fromRequest(new EmployeeSkillMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<EmployeeSkillIndex> ref)
            throws ViewBuilderException, IOException {
        EmployeeSkillMapper mapper = ctx.query(EmployeeSkillMapper.class);
        EmployeeSkillMask mask = ctx.query(EmployeeSkillMask.class);
        List<EmployeeSkill> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (EmployeeSkill o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
