package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.sea.FooVbo;

public class PersonVbo
        extends FooVbo<Person> {

    public PersonVbo() {
        super(Person.class);
    }

    @Override
    protected IHtmlTag afterForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Person> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Person person = ref.get();

        PathFieldMap subIndexFields;
        {
            IType itemType = PotatoTypes.getInstance().forClass(Contact.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            subIndexFields = new PathFieldMap(itemFormDecl);
            try {
                subIndexFields.parse("i*", "rename", "usage", "region", "tel", "mobile", "fax", "email", "web", "qq",
                        "postalCode", "address1", "address2");
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        IndexTable itab = new IndexTable(out, "items");
        itab.attr("dom", "rti");
        itab.attr("no-colvis", "1");
        itab.setAjaxMode(false);
        itab.setFootColumns(false);
        itab.buildHeader(ctx, subIndexFields.values());

        for (Contact o : person.getContacts()) {
            HtmlTrTag tr = itab.tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getRename());
            tr.td().text(o.getUsage());
            tr.td().text(o.getRegion());
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

        return out;
    }

}
