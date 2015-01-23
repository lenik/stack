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
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class PersonIndexVbo
        extends Zc3Template_CEM<PersonIndex, Person> {

    public PersonIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(PersonIndex.class);
        indexFields.parse("i*sa", "ageSexLoc", "typeChars", "fullName", "description", //
                "contact.fullAddress", "contact.tels", "contact.qq");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Person> a, IUiRef<PersonIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        PersonMapper mapper = ctx.query(PersonMapper.class);

        PersonCriteria criteria = fn.criteriaFromRequest(new PersonCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchPairs("类型", false, //
                    PartyType.list, "type", criteria.type, false);
            criteria.type = so.key;

            SwitchOverride<String> so1;
            so1 = filters.switchPairs("姓氏", true, //
                    mapper.histoBySurname(), "surname", criteria.surname, false);
            criteria.surname = so1.key;
        }

        List<Person> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Person o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getAgeSexLoc());
                tr.td().text(o.getTypeChars());
                tr.td().b().text(o.getFullName());
                tr.td().text(o.getDescription()).class_("small");

                Contact contact = o.getContact();
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

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
