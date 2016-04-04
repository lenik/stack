package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class ContactIndex_htm
        extends SlimIndex_htm<ContactIndex, Contact, ContactMask> {

    public static final String[] FIELDS = { "priority", "rename", "usage",
            // "region",
            "tel", "mobile", "fax", "email", "web", "qq", "postalCode", "address1", "address2" };

    public ContactIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(ContactIndex.class);
        indexFields.parse("i*", FIELDS);
    }

    @Override
    protected ContactMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ContactMask mask = MaskBuilder.fromRequest(new ContactMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Contact> a, IUiRef<ContactIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        ContactMapper mapper = ctx.query(ContactMapper.class);
        ContactMask mask = ctx.query(ContactMask.class);
        List<Contact> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Contact o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getPriority());
                tr.td().text(o.getRename());
                tr.td().text(o.getUsage());

                // tr.td().text(o.getRegion());
                // tr.td().text(o.getCountry());
                // tr.td().text(o.getR1());
                // tr.td().text(o.getR2());
                // tr.td().text(o.getR3());

                tr.td().text(o.getTel());
                tr.td().text(o.getMobile());
                tr.td().text(o.getFax());
                tr.td().text(o.getEmail());
                tr.td().text(o.getWeb());
                tr.td().text(o.getQq());

                tr.td().text(o.getPostalCode());
                tr.td().text(o.getAddress1());
                tr.td().text(o.getAddress2());
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }
}
