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
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class PersonIndex_htm
        extends SlimIndex_htm<PersonIndex, Person, PersonMask> {

    public PersonIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(PersonIndex.class);
        indexFields.parse("i*sa", "ageSexLoc", "typeChars", "fullName", "description", //
                "contact0.fullAddress", "contact0.tels", "contact0.qq");
    }

    @Override
    protected PersonMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        PersonMapper mapper = ctx.query(PersonMapper.class);
        PersonMask mask = MaskBuilder.fromRequest(new PersonMask(), ctx.getRequest());

        SwitcherModel<Integer> sw1;
        sw1 = switchers.entryOf("类型", false, //
                PartyType.list, "type", mask.type, false);
        mask.type = sw1.getSelection1();

        SwitcherModel<String> sw2;
        sw2 = switchers.entryOf("姓氏", true, //
                mapper.histoBySurname(), "surname", mask.surname, false);
        mask.surname = sw2.getSelection1();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<PersonIndex> ref)
            throws ViewBuilderException, IOException {
        PersonMapper mapper = ctx.query(PersonMapper.class);
        PersonMask mask = ctx.query(PersonMask.class);
        List<Person> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Person o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getAgeSexLoc());
            tr.td().text(o.getTypeChars());
            tr.td().b().text(o.getFullName());
            tr.td().class_("small").text(o.getDescription());

            Contact contact = o.getContact0();
            if (contact == null) {
                for (int i = 0; i < 3; i++)
                    tr.td();
            } else {
                tr.td().class_("small").text(contact.getFullAddress());
                tr.td().text(contact.getTels());
                tr.td().text(contact.getQq());
            }
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
