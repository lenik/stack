package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(Contact.class)
public class ContactIndex
        extends QuickIndex {

    public ContactIndex(IQueryable context) {
        super(context);
    }

}
