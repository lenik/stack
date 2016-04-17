package com.bee32.zebra.tk.hbin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlTable;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTh;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.PathField;
import net.bodz.bas.repr.form.SortOrder;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.mx.base.CoMessage;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.site.FormatFn;

public class IndexTable
        // extends AbstractHtmlViewBuilder<Object>
        implements IFontAwesomeCharAliases {

    Map<String, Object> attrs;

    public String ajaxUrl;
    Iterable<PathField> indexFields;
    boolean headColumns = true;
    boolean footColumns = true;

    Set<String> detailFields = new HashSet<>();
    FormatFn fmt = new FormatFn();

    public IndexTable(IHtmlViewContext ctx, Iterable<PathField> indexFields) {
        attrs = new LinkedHashMap<>();

        detailFields.add("accessMode");
        detailFields.add("creationDate");
        detailFields.add("endDate");
        detailFields.add("flags");
        detailFields.add("owner");
        detailFields.add("ownerGroup");
        detailFields.add("state");

        setAjaxUrlFromRequest(ctx.getRequest());
        this.indexFields = indexFields;
    }

    public HtmlTbody buildViewStart(IHtmlOut out) {
        return buildViewStart(out, "itab");
    }

    public void buildViewEnd(IHtmlOut tbody) {
        buildViewEnd(tbody, "itab");
    }

    public HtmlTbody buildViewStart(IHtmlOut out, String id) {
        out = out.div();

        if (id != null)
            out.id(id);

        out.class_("itab");
        for (String k : attrs.keySet())
            out.attr(k, attrs.get(k));

        HtmlTable table = out.table();
        // table.style("width: 100%");
        table.class_("table table-striped table-hover table-condensed dataTable table-responsive");

        if (ajaxUrl != null)
            table.dataUrl(ajaxUrl);

        if (headColumns)
            buildFieldCols(table.thead());

        return table.tbody();
    }

    public void buildViewEnd(IHtmlOut tbody, String id) {
        IHtmlOut table = tbody.end();

        if (footColumns)
            buildFieldCols(table.tfoot());

        IHtmlOut out = table.end();
        HtmlDiv tools = out.div().class_("tools");
        buildTools(tools, id);

        EditDialog_htm1 editdlg1 = new EditDialog_htm1();
        editdlg1.parentItabId = id;
        editdlg1.build(tools, id + "ed");
    }

    protected void buildTools(HtmlDiv tools, String tabId) {
    }

    public void setAjaxUrlFromRequest(HttpServletRequest req) {
        StringBuilder url = new StringBuilder();
        url.append("data.json");
        String query = req.getQueryString();
        if (query != null)
            url.append("?" + query);
        ajaxUrl = url.toString();
    }

    void buildFieldCols(IHtmlOut tr) {
        for (PathField pathField : indexFields) {
            IFieldDecl fieldDecl = pathField.getFieldDecl();
            HtmlTh th = tr.th().text(IXjdocElement.fn.labelName(fieldDecl));
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

    public void cocols(String spec, HtmlTr tr, CoObject o) {
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
                ref(tr.td(), o.getOwnerUser()).class_("small");
                ref(tr.td(), o.getOwnerGroup()).class_("small");
                break;

            default:
                throw new IllegalArgumentException("Bad column group specifier: " + c);
            }
        }
    }

    protected <tag_t extends IHtmlOut> tag_t ref(tag_t tag, CoObject e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

}
