package com.bee32.zebra.tk.hbin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTableTag;
import net.bodz.bas.html.dom.tag.HtmlTbodyTag;
import net.bodz.bas.html.dom.tag.HtmlThTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.PathField;
import net.bodz.bas.repr.form.SortOrder;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.site.FormatFn;
import com.tinylily.model.base.CoObject;
import com.tinylily.model.mx.base.CoMessage;

public class IndexTable
        extends HtmlTableTag {

    public IHtmlTag[] headFoot;
    public HtmlTbodyTag tbody;

    Set<String> detailFields = new HashSet<>();
    FormatFn fmt = new FormatFn();

    public IndexTable(IHtmlTag parent) {
        this(parent, "list");
    }

    public IndexTable(IHtmlTag parent, String id) {
        super(parent, "table");

        this.id(id);
        this.class_("table table-striped table-hover table-condensed dataTable");

        headFoot = new IHtmlTag[] { this.thead(), this.tfoot() };
        tbody = this.tbody();

        detailFields.add("accessMode");
        detailFields.add("creationDate");
        detailFields.add("endDate");
        detailFields.add("flags");
        detailFields.add("owner");
        detailFields.add("ownerGroup");
        detailFields.add("state");
    }

    public void buildHeader(IHtmlViewContext ctx, Iterable<PathField> indexFields) {
        boolean ajaxMode = true;
        if (ajaxMode) {
            StringBuilder url = new StringBuilder();
            url.append("data.json");
            String query = ctx.getRequest().getQueryString();
            if (query != null)
                url.append("?" + query);
            this.dataUrl(url);
        }

        for (IHtmlTag tr : this.headFoot)
            for (PathField pathField : indexFields) {
                IFieldDecl fieldDecl = pathField.getFieldDecl();
                HtmlThTag th = tr.th().text(IXjdocElement.fn.labelName(fieldDecl));
                th.dataField(fieldDecl.getName());

                Set<String> classes = new LinkedHashSet<String>();
                String styleClass = fieldDecl.getStyleClass();
                if (styleClass != null)
                    classes.add(styleClass);

                boolean sortable = fieldDecl.getPreferredSortOrder() != SortOrder.NO_SORT;
                th.dataSortable(sortable);
                if (!sortable)
                    classes.add("no-sort");

                fieldOverride(fieldDecl, classes);

                if (!classes.isEmpty())
                    th.class_(StringArray.join(" ", classes));
            }
    }

    protected void fieldOverride(IFieldDecl fieldDecl, Set<String> classes) {
        if (detailFields.contains(fieldDecl.getName()))
            classes.add("detail");
    }

    public void addDetailFields(String... fieldNames) {
        detailFields.addAll(Arrays.asList(fieldNames));
    }

    public void cocols(String spec, HtmlTrTag tr, CoObject o) {
        for (char c : spec.toCharArray()) {
            switch (c) {
            case 'i':
                tr.td().text(o.getId()).class_("zu-id");
                break;

            case 'c':
                tr.td().text(o.getCodeName());
                break;

            case 'u':
                tr.td().b().text(o.getLabel()).class_("small").style("max-width: 20em");
                tr.td().text(Strings.ellipsis(o.getDescription(), 50)).class_("small").style("max-width: 30em");
                break;

            case 'm':
                CoMessage<?> message = (CoMessage<?>) o;
                tr.td().b().text(message.getSubject()).class_("small").style("max-width: 20em");
                tr.td().text(Strings.ellipsis(message.getText(), 50)).class_("small").style("max-width: 30em");
                break;

            case 's':
                tr.td().text(o.getPriority());
                tr.td().text(fmt.formatDate(o.getCreationTime())).class_("small");
                tr.td().text(fmt.formatDate(o.getLastModified())).class_("small");
                tr.td().text(o.getFlags());
                tr.td().text(o.getState().getName());
                break;

            case 'a':
                int mode = o.getAccessMode();
                tr.td().text(mode).class_("small");
                fmt.ref(tr.td(), o.getOwner()).class_("small");
                fmt.ref(tr.td(), o.getOwnerGroup()).class_("small");
                break;

            default:
                throw new IllegalArgumentException("Bad column group specifier: " + c);
            }
        }
    }

}
