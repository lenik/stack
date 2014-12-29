package com.bee32.zebra.tk.site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.db.batis.IMapperTemplate;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.AbstractHtmlTag;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.potato.element.IPropertyAccessor;
import net.bodz.bas.repr.form.FieldGroup;
import net.bodz.bas.repr.form.FormFieldBuilder;
import net.bodz.bas.repr.form.FormFieldListBuilder;
import net.bodz.bas.repr.form.IFormField;
import net.bodz.bas.repr.form.IFormStruct;
import net.bodz.bas.repr.form.SortOrder;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.sea.MapperUtil;
import com.tinylily.model.base.CoEntity;
import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.mx.base.CoMessage;
import com.tinylily.repr.CoEntityManager;

public abstract class Zc3Template_CEM<M extends CoEntityManager, T>
        extends AbstractHtmlViewBuilder<M>
        implements IZebraSiteAnchors, IZebraSiteLayout {

    protected IFormStruct formStruct;
    protected List<IFormField> indexFields;

    public Zc3Template_CEM(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    protected void insertIndexFields(String spec, String... pathProperties)
            throws NoSuchPropertyException, ParseException {
        FormFieldBuilder formFieldBuilder = new FormFieldBuilder();
        FormFieldListBuilder builder = new FormFieldListBuilder(formFieldBuilder);

        for (char c : spec.toCharArray()) {
            switch (c) {
            case 'i':
                builder.fromPathProperties(formStruct, "id");
                break;
            case 's':
                builder.fromPathProperties(formStruct, "priority", "creationTime", "lastModified", "flags", "state");
                break;
            case 'a':
                builder.fromPathProperties(formStruct, "accessMode", "owner", "ownerGroup");
                break;
            case '*':
                builder.fromPathProperties(formStruct, pathProperties);
                break;
            default:
                throw new IllegalArgumentException("Bad column group specifier: " + c);
            }
        }
        indexFields = builder.getFields();
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<M> ref, IOptions options)
            throws ViewBuilderException, IOException {

        IHtmlTag body1 = ctx.getTag(ID.body1);

        HtmlDivTag mainCol = body1.div().id(ID.main_col).class_("col-xs-12 col-sm-9 col-lg-10");
        {
            HtmlDivTag headDiv = mainCol.div().id("zp-head").class_("info clearfix");
            headDiv.div().id(ID.title);

            HtmlDivTag headCol1 = headDiv.div().id("zp-head-col1").class_("col-xs-6");
            headCol1.div().id(ID.stat);

            HtmlDivTag cmdsDiv = headCol1.div().id("zp-cmds");
            cmdsDiv.div().id(ID.cmds0);
            cmdsDiv.div().id(ID.cmds1);

            HtmlDivTag headCol2 = headDiv.div().id("zp-head-col2").class_("col-xs-6");
            HtmlDivTag headLinks = headCol2.div().id("zp-links");
            headLinks.span().text("您可能需要进行下面的操作:");
            headLinks.ul().id(ID.links_ul);
        }

        HtmlDivTag rightCol = body1.div().id(ID.right_col).class_("hidden-xs col-sm-3 col-lg-2 info");
        {
            HtmlDivTag previewDiv = rightCol.div().id("zp-preview").align("center");
            previewDiv.img().src("pic.png");

            HtmlDivTag infosel = rightCol.div().id(ID.infosel);

            HtmlTableTag _table1 = infosel.table().width("100%").class_("zu-layout");
            HtmlTrTag _tr1 = _table1.tr();
            HtmlTdTag _td1 = _tr1.td();
            _td1.h2().text("选中的信息");
            HtmlTdTag _td2 = _tr1.td().align("right").style("width: 3em");
            _td2.a().href("").text("[编辑]");

            infosel.div().id(ID.infoselData);

            HtmlDivTag refdocsDiv = rightCol.div().id("zp-infoman");
            refdocsDiv.h2().text("管理文献");
            refdocsDiv.ul().id(ID.infoman_ul);
        }

        titleInfo(ctx, ref);
        return ctx;
    }

    protected IndexTable mkIndexTable(IHtmlTag parent, String id) {
        IndexTable table = new IndexTable(parent, id);

        for (IHtmlTag thr : table.headFoot)
            for (IFormField field : indexFields) {
                HtmlThTag th = thr.th().text(IXjdocElement.fn.labelName(field));
                List<String> classes = new ArrayList<String>();

                switch (field.getName()) {
                case "accessMode":
                case "creationTime":
                case "endTime":
                case "flags":
                case "owner":
                case "ownerGroup":
                case "state":
                    classes.add("detail");
                }

                boolean sortable = field.getPreferredSortOrder() != SortOrder.NO_SORT;
                th.dataSortable(sortable);
                if (!sortable)
                    classes.add("no-sort");

                String styleClass = field.getStyleClass();
                if (styleClass != null)
                    classes.add(styleClass);

                if (!classes.isEmpty())
                    th.class_(StringArray.join(" ", classes));
            }

        return table;
    }

    protected void titleInfo(IHtmlViewContext ctx, IUiRef<M> ref) {
        M manager = ref.get();
        Class<?> entityType = manager.getEntityType();
        IMapperTemplate<?, ?> mapper = MapperUtil.getMapperTemplate(entityType);

        ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(getValueType());
        PageStruct p = new PageStruct(ctx);
        p.title.h1().text(classDoc.getTag("label"));

        iString docText = classDoc.getText();
        HtmlPTag subTitle = p.title.p().class_("sub");
        subTitle.verbatim(docText.getHeadPar());

        Map<String, Long> countMap = mapper.count();
        HtmlUlTag statUl = p.stat.ul();
        for (String id : countMap.keySet()) {
            long count = countMap.get(id);
            String name = id;
            switch (id) {
            case "total":
                name = "总计";
                break;
            case "valid":
                name = "有效";
                break;
            case "used":
                name = "在用";
                break;
            case "locked":
                name = "锁定";
                break;
            }
            statUl.li().text(name + " " + count + " 种");
        }

        p.cmds0.a().href("new/").text("新建");
        p.cmds0.a().href("export/").text("导出");
        p.cmds0.a().href("print/").text("打印");
        p.cmds1.a().href("barcode/").text("打印条码");

        List<String> rels = classDoc.getTag("rel", List.class);
        if (rels != null)
            for (String rel : rels) {
                int colon = rel.indexOf(':');
                IAnchor href = _webApp_.join(rel.substring(0, colon).trim());
                String text = rel.substring(colon + 1).trim();
                p.linksUl.li().a().href(href.toString()).text(text);
            }

        List<String> seeList = classDoc.getTag("see", List.class);
        if (seeList != null)
            for (String see : seeList)
                p.infomanUl.li().verbatim(see);

    }

    protected void cocols(String spec, HtmlTrTag tr, CoEntity o) {
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
                CoMessage message = (CoMessage) o;
                tr.td().b().text(message.getSubject()).class_("small").style("max-width: 20em");
                tr.td().text(Strings.ellipsis(message.getText(), 50)).class_("small").style("max-width: 30em");
                break;

            case 's':
                tr.td().text(o.getPriority());
                tr.td().text(fn.formatDate(o.getCreationTime())).class_("small");
                tr.td().text(fn.formatDate(o.getLastModified())).class_("small");
                tr.td().text(o.getFlags());
                tr.td().text(o.getState().getName());
                break;

            case 'a':
                int mode = o.getAccessMode();
                tr.td().text(mode).class_("small");
                ref(tr.td(), o.getOwner()).class_("small");
                ref(tr.td(), o.getOwnerGroup()).class_("small");
                break;

            default:
                throw new IllegalArgumentException("Bad column group specifier: " + c);
            }
        }
    }

    protected void dumpFullData(IHtmlTag parent, Collection<? extends CoEntity> dataset) {
        int count = 0;
        Map<FieldGroup, Collection<IFormField>> fgMap = formStruct.getFieldsGrouped(DetailLevel.EXTEND);
        for (CoEntity entity : dataset) {
            if (count++ > 20)
                break;
            HtmlDivTag dtab = parent.div().id("data-" + entity.getId());
            for (FieldGroup fg : fgMap.keySet()) {
                Collection<IFormField> fields = fgMap.get(fg);
                if (fields.isEmpty())
                    continue;

                String fgLabel = fg == FieldGroup.NULL ? "基本信息" : IXjdocElement.fn.labelName(fg);
                dtab.h3().class_("zu-fgroup").text(fgLabel);

                for (IFormField field : fields) {
                    IPropertyAccessor accessor = field.getAccessor();
                    Object value;
                    try {
                        value = accessor.getValue(entity);
                        if (value == null)
                            switch (field.getNullConvertion()) {
                            case EMPTY:
                                value = "";
                            default:
                            }
                    } catch (ReflectiveOperationException e) {
                        value = "(" + e.getClass().getName() + ") " + e.getMessage();
                    }

                    HtmlDivTag line = dtab.div();
                    line.span().class_("zu-flabel").text(IXjdocElement.fn.labelName(field) + ": ");
                    line.span().text(value);
                } // for field
            } // for field-group
        } // for entity
    }

    protected List<T> postfilt(List<T> list) {
        return list;
    }

    protected <tag_t extends AbstractHtmlTag<?>> tag_t ref(tag_t tag, CoEntity e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

    protected <C extends CoEntityCriteria> C criteriaFromRequest(C criteria, HttpServletRequest req)
            throws ViewBuilderException {
        try {
            criteria.populate(req.getParameterMap());
        } catch (ParseException e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }
        return criteria;
    }

    protected static class fn {

        public static String labels(Collection<? extends CoEntity> entities) {
            if (entities == null)
                return null;
            StringBuilder sb = new StringBuilder(entities.size() * 80);
            for (CoEntity o : entities) {
                if (sb.length() != 0)
                    sb.append(", ");
                sb.append(o.getLabel());
            }
            return sb.toString();
        }

        public static String formatDate(Object date) {
            if (date == null)
                return null;
            else
                return Dates.YYYY_MM_DD.format(date);
        }

    }

}
