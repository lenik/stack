package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class ContactVbo
        extends SlimForm_htm<Contact> {

    public ContactVbo() {
        super(Contact.class);
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        if (group.getCategory() == FieldCategory.NULL) {
            excludes.add("codeName");
            excludes.add("label");
            excludes.add("description");
        }
    }

}
