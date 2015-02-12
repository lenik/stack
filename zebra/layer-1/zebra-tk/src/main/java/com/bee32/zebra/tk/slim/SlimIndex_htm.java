package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONWriter;

import net.bodz.bas.c.type.TypeParam;
import net.bodz.bas.db.ibatis.IMapperTemplate;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.dom.AbstractHtmlTag;
import net.bodz.bas.html.dom.HtmlDoc;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlHeadData;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.potato.element.IPropertyAccessor;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclFilters;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.req.HttpSnap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.std.rfc.mime.ContentType;
import net.bodz.bas.std.rfc.mime.ContentTypes;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.bas.xml.dom.XmlTags;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.repr.QuickIndexFormat;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.FormatFn;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.sql.MapperUtil;
import com.bee32.zebra.tk.util.Counters;
import com.bee32.zebra.tk.util.Table2JsonFormatter;
import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.CoObjectCriteria;

public abstract class SlimIndex_htm<X extends QuickIndex, T, C>
        extends AbstractHtmlViewBuilder<X>
        implements IZebraSiteAnchors, IZebraSiteLayout, IArtifactConsts, IFontAwesomeCharAliases {

    protected IFormDecl formDecl;
    protected PathFieldMap indexFields;

    public SlimIndex_htm(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
        Class<?> param1 = TypeParam.infer1(getClass(), SlimIndex_htm.class, 1);
        formDecl = IFormDecl.fn.forClass(param1);
        indexFields = new PathFieldMap(formDecl);
    }

    @Override
    public ContentType getContentType(HttpServletRequest request, X value) {
        switch (value.format) {
        case JSON:
            return ContentTypes.text_javascript;
        default:
        }
        return super.getContentType(request, value);
    }

    @Override
    public boolean isOrigin(X value) {
        return false; // value.format != FooIndexFormat.HTML;
    }

    @Override
    public boolean isFrame() {
        return true;
    }

    @Override
    public void preview(IHtmlViewContext ctx, IUiRef<X> ref, IOptions options) {
        super.preview(ctx, ref, options);
        IHtmlHeadData metaData = ctx.getHeadData();
        // metaData.addDependency("datatables.bootstrap.js", SCRIPT);
        // metaData.addDependency("datatables.responsive.js", SCRIPT);
        metaData.addDependency("all-data", PSEUDO);
        // metaData.addDependency("datatables.tableTools.js", SCRIPT);
    }

    @Override
    public final IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, IUiRef<X> ref, IOptions options)
            throws ViewBuilderException, IOException {
        X index = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(index).getRemainingPath() == null;
        if (arrivedHere && index.defaultPage && enter(ctx))
            return out;

        SwitcherModelGroup switchers = new SwitcherModelGroup();
        CoObjectCriteria criteria = buildSwitchers(ctx, switchers);
        ctx.getRequest().setAttribute(criteria.getClass().getName(), criteria);

        if (index.format == QuickIndexFormat.JSON) {
            PrintWriter writer = ctx.getResponse().getWriter();
            buildJson(ctx, writer, ref, options);
            return null;
        }

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (!layout.hideFramework) {
            IHtmlTag body1 = ctx.getTag(ID.body1);
            {
                HtmlDivTag headDiv = body1.div().id(ID.head).class_("zu-info clearfix");
                headDiv.div().id(ID.title);

                HtmlDivTag headCol1 = headDiv.div().id(ID.headCol1).class_("col-xs-6");
                headCol1.div().id(ID.stat);

                HtmlDivTag cmdsDiv = headCol1.div().id(ID.cmds);
                cmdsDiv.div().id(ID.cmds0);
                cmdsDiv.div().id(ID.cmds1);

                headDiv.div().id(ID.headCol2).class_("col-xs-6");

                out = body1;
            }

            IHtmlTag rightCol = ctx.getTag(ID.right_col);
            if (arrivedHere) {
                HtmlDivTag previewDiv = rightCol.div().id(ID.preview).align("center");
                // previewDiv.div().class_("icon fa").text(FA_COFFEE);
                previewDiv.img().src(_webApp_.join("zebra/scene1.png").absoluteHref());

                HtmlDivTag infosel = rightCol.div().id(ID.infosel);

                HtmlTableTag _table1 = infosel.table().width("100%").class_("zu-layout");
                HtmlTrTag _tr1 = _table1.tr();
                HtmlTdTag _td1 = _tr1.td();
                _td1.h2().text("选中的信息");
                HtmlTdTag _td2 = _tr1.td().align("right").style("width: 3.5em");

                _td2.a().id("zp-infosel-edit").href("").target("_blank").text("[编辑]");

                infosel.div().id(ID.infoselData);

                HtmlDivTag refdocsDiv = rightCol.div().id(ID.infoman);
                refdocsDiv.h2().text("管理文献");
                refdocsDiv.ul().id(ID.infoman_ul);
            }
        } // showFramework

        PageStruct page = new PageStruct(ctx);
        DataViewAnchors<T> a = new DataViewAnchors<T>();
        if (index.format == QuickIndexFormat.HTML) {
            a.frame = page.body1;
            a.data = page.body1;
            a.extradata = page.extradata;
            a.dataList = false;
        } else {
            a.frame = out;
            a.data = out;
            a.extradata = null;
            a.dataList = false;
        }

        if (arrivedHere) {
            FilterSectionDiv filtersDiv = new FilterSectionDiv(a.frame, "s-filter");
            filtersDiv.build(switchers);
        }

        if (!layout.hideFramework)
            titleInfo(ctx, ref, arrivedHere);

        if (arrivedHere)
            dataIndex(ctx, a, ref, options);

        afterData(ctx, out, ref, options);

        if (arrivedHere) {
            new PageStruct(ctx);
            page.scripts.script().javascriptSrc(getClass().getSimpleName() + ".js");
        }

        return out;
    }

    protected abstract CoObjectCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException;

    protected void titleInfo(IHtmlViewContext ctx, IUiRef<X> ref, boolean indexPage) {
        X manager = ref.get();
        Class<?> objectType = manager.getObjectType();
        IMapperTemplate<?, C> mapper = MapperUtil.getMapperTemplate(objectType);

        ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(getValueType());
        PageStruct p = new PageStruct(ctx);
        p.title.h1().text(classDoc.getTag("label"));

        iString docText = classDoc.getText();
        HtmlPTag subTitle = p.title.p().class_("sub");
        subTitle.verbatim(docText.getHeadPar());

        Class<C> criteriaClass = (Class<C>) CoObjectCriteria.findCriteriaClass(objectType);
        if (criteriaClass == null)
            throw new IllegalUsageException("No criteria for " + objectType);
        C criteria = ctx.query(criteriaClass);

        Map<String, Number> countMap = mapper.count(criteria);
        HtmlUlTag statUl = p.stat.ul();
        for (String key : countMap.keySet()) {
            String name = Counters.displayName(key);
            long count = countMap.get(key).longValue();
            statUl.li().text(name + " " + count + " 条");
        }

        if (indexPage) {
            p.cmds0.a().href("new/").target("_blank").text("新建");
            p.cmds0.a().href("?view:=csv").text("导出").style("cursor: default; color: gray").onclick("return false");
            p.cmds0.a().href("javascript: window.print()").text("打印");
        } else {
            HtmlATag closeLink = p.cmds0.a().href("javascript: window.close()");
            closeLink.span().class_("fa icon").text(FA_TIMES_CIRCLE);
            closeLink.text("关闭本页面");

            HtmlATag upLink = p.cmds0.a().href("../");
            upLink.span().class_("fa icon").text(FA_LEVEL_UP);
            upLink.text("回到上一层");

            p.cmds1.br();
            HtmlATag submitLink = p.cmds1.a().href("javascript: form.submit()");
            submitLink.span().class_("fa icon").text(FA_FLOPPY_O);
            submitLink.text("提交").title("将输入的数据提交保存。");

            HtmlATag refreshLink = p.cmds1.a().href("javascript: location.href = location.href").onclick("spin(this)");
            refreshLink.span().class_("fa icon").text(FA_REFRESH);
            refreshLink.text("刷新").title("重新载入当前页面。");
        }

        List<String> rels = classDoc.getTag("rel", List.class);
        if (rels != null && !rels.isEmpty()) {
            IHtmlTag headCol2 = ctx.getTag(ID.headCol2);
            HtmlDivTag headLinks = headCol2.div().class_("zu-links");
            headLinks.div().text("您可能需要进行下面的操作:");
            HtmlUlTag linksUl = headLinks.ul();
            for (String rel : rels) {
                int colon = rel.indexOf(':');
                IAnchor href = _webApp_.join(rel.substring(0, colon).trim());
                String text = rel.substring(colon + 1).trim();
                linksUl.li().a().href(href).text(text);
            }
        }

        if (indexPage) {
            List<String> seeList = classDoc.getTag("see", List.class);
            if (seeList != null)
                for (String see : seeList)
                    p.infomanUl.li().verbatim(see);
        }
    }

    protected abstract void dataIndex(IHtmlViewContext ctx, DataViewAnchors<T> a, IUiRef<X> ref, IOptions options)
            throws ViewBuilderException, IOException;

    protected void afterData(IHtmlViewContext ctx, IHtmlTag out, IUiRef<X> ref, IOptions options)
            throws ViewBuilderException, IOException {
    }

    protected void buildJson(IHtmlViewContext ctx, PrintWriter out, IUiRef<X> ref, IOptions options)
            throws ViewBuilderException, IOException {
        DataViewAnchors<T> a = new DataViewAnchors<T>();
        a.frame = new HtmlDoc();
        a.data = new HtmlDoc();
        a.dataList = true;
        dataIndex(ctx, a, ref, options);

        IHtmlTag table = null;
        table = (IHtmlTag) XmlTags.findFirst(a.data, "table");

        JSONWriter jw = new JSONWriter(out);
        Table2JsonFormatter fmt = new Table2JsonFormatter(jw);
        fmt.format(table);
    }

    protected void dumpFullData(IHtmlTag parent, Collection<? extends CoObject> dataset) {
        int count = 0;
        Collection<FieldDeclGroup> groups = formDecl
                .getFieldGroups(FieldDeclFilters.maxDetailLevel(DetailLevel.DETAIL));
        for (CoObject entity : dataset) {
            if (count++ > 3)
                break;
            HtmlDivTag dtab = parent.div().id("data-" + entity.getId());
            for (FieldDeclGroup group : groups) {
                if (group.isEmpty())
                    continue;

                FieldCategory category = group.getCategory();
                String catLabel = category == FieldCategory.NULL ? "基本信息" : IXjdocElement.fn.labelName(category);
                HtmlDivTag groupDiv = dtab.div().class_("zu-fgroup").style("line-heignt: 2em");
                groupDiv.h3().text(catLabel);

                for (IFieldDecl fieldDecl : group) {
                    IPropertyAccessor accessor = fieldDecl.getAccessor();
                    Object value;
                    try {
                        value = accessor.getValue(entity);
                        if (value == null)
                            switch (fieldDecl.getNullConvertion()) {
                            case EMPTY:
                                value = "";
                                break;
                            default:
                                continue;
                            }
                    } catch (ReflectiveOperationException e) {
                        value = "(" + e.getClass().getName() + ") " + e.getMessage();
                    }

                    HtmlDivTag line = groupDiv.div();
                    line.span().class_("zu-flabel zu-w50").text(IXjdocElement.fn.labelName(fieldDecl) + ": ");
                    line.span().text(value);
                } // for field
            } // for field-group
        } // for entity
    }

    protected List<T> postfilt(List<T> list) {
        return list;
    }

    protected <tag_t extends AbstractHtmlTag<?>> tag_t ref(tag_t tag, CoObject e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

    protected final FormatFn fmt = new FormatFn();

    protected static class fn {

        public static <C extends CoObjectCriteria> C criteriaFromRequest(C criteria, HttpServletRequest req)
                throws ViewBuilderException {
            try {
                HttpSnap snap = (HttpSnap) req.getAttribute(HttpSnap.class.getName());
                if (snap == null)
                    criteria.populate(req.getParameterMap());
                else
                    criteria.populate(snap.getParameterMap());
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            return criteria;
        }

        public static String labels(Collection<? extends CoObject> entities) {
            if (entities == null)
                return null;
            StringBuilder sb = new StringBuilder(entities.size() * 80);
            for (CoObject o : entities) {
                if (sb.length() != 0)
                    sb.append(", ");
                sb.append(o.getLabel());
            }
            return sb.toString();
        }

    }

}
