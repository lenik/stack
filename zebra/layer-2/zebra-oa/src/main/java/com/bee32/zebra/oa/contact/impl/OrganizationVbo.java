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
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class OrganizationVbo
        extends SlimForm_htm<Organization> {

    public OrganizationVbo() {
        super(Organization.class);
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<Organization> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Organization org = ref.get();

        if (org.getId() != null) {
            IType itemType = PotatoTypes.getInstance().forClass(Contact.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }

            PathFieldMap orgFields = new PathFieldMap(itemFormDecl);
            try {
                orgFields.parse("i*", ContactIndexVbo.FIELDS);
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }

            ItemsTable xtab = new ItemsTable(out);
            xtab.ajaxUrl = "../../contact/data.json?org=" + org.getId();
            xtab.buildHeader(orgFields.values());
        }

        return out;
    }

}
