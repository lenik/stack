package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class OrganizationIndex_htm
        extends SlimIndex_htm<OrganizationIndex, Organization, OrganizationMask> {

    public OrganizationIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(OrganizationIndex.class);
        indexFields.parse("i*sa", "typeChars", "fullName", "size", "description", //
                "contact0.fullAddress", "contact0.tels", "contact0.qq");
    }

    @Override
    protected OrganizationMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        OrganizationMask mask = VarMapState.restoreFrom(new OrganizationMask(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entryOf("类型", false, //
                PartyType.list, "type", mask.type, false);
        mask.type = sw.getSelection1();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<OrganizationIndex> ref)
            throws ViewBuilderException, IOException {
        OrganizationMapper mapper = ctx.query(OrganizationMapper.class);
        OrganizationMask mask = ctx.query(OrganizationMask.class);
        List<Organization> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Organization o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getTypeChars());
            tr.td().b().text(o.getFullName());
            tr.td().text(o.getSize());
            tr.td().text(o.getDescription()).class_("small");

            Contact contact = o.getContact0();
            if (contact == null) {
                for (int i = 0; i < 3; i++)
                    tr.td();
            } else {
                tr.td().text(contact.getFullAddress()).class_("small");
                tr.td().text(contact.getTels());
                tr.td().text(contact.getQq());
            }
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
