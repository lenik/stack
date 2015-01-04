package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONWriter;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.PathField;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class PersonManagerVbo
        extends Zc3Template_CEM<PersonManager, Person> {

    public PersonManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(PersonManager.class);
        insertIndexFields("i*sa", "ageSexLoc", "typeChars", "fullName", "description", //
                "contact.fullAddress", "contact.tels", "contact.qq");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<PersonManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        PersonMapper mapper = ctx.query(PersonMapper.class);

        PersonCriteria criteria = criteriaFromRequest(new PersonCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(page.mainCol, "s-filter");
        {
            SwitchOverride<String> so;
            so = filters.switchPairs("姓氏", true, //
                    mapper.histoBySurname(), "surname", criteria.surname, false);
            criteria.surname = so.key;
        }

        List<Person> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");

        for (Person o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
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
            cocols("sa", tr, o);
        }

        // dumpFullData(page.extradata, list);
    }

    @Override
    protected void buildJson(IHtmlViewContext ctx, PrintWriter out, IUiRef<PersonManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        PersonMapper mapper = ctx.query(PersonMapper.class);
        PersonCriteria criteria = criteriaFromRequest(new PersonCriteria(), ctx.getRequest());
        List<Person> list = postfilt(mapper.filter(criteria));

        JSONWriter jw = new JSONWriter(out);
        jw.array();
        for (Person p : list) {
            jw.array();

            // for (IFieldDecl fieldDef : formDecl.getFieldDefs()) {
            for (PathField pathField : indexFields) {
                try {
                    Object value = pathField.getValue(p);
                    if (value == null) {
                        jw.value(null);
                    } else {
                        jw.value(value.toString());
                    }
                } catch (ReflectiveOperationException e) {
                    throw new ViewBuilderException(e.getMessage(), e);
                }
            }

            jw.endArray(); // column
        }
        jw.endArray(); // row
    }

}
