package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.IHttpViewContext;
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
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class PersonVbo
        extends SlimForm_htm<Person> {

    public PersonVbo() {
        super(Person.class);
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<Person> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Person person = ref.get();

        if (person.getId() != null) {
            IType itemType = PotatoTypes.getInstance().forClass(Contact.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }

            PathFieldMap contactFields = new PathFieldMap(itemFormDecl);
            try {
                contactFields.parse("i*", ContactIndexVbo.FIELDS);
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }

            ItemsTable xtab = new ItemsTable(out);
            xtab.ajaxUrl = "../../contact/data.json?person=" + person.getId();
            xtab.buildHeader(contactFields.values());
        }

        return out;
    }

}
