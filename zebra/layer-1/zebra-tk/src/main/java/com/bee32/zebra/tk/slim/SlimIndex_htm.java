package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONWriter;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.c.type.TypeParam;
import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.bas.db.ibatis.IMapperTemplate;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.dom.IHtmlHeadData;
import net.bodz.bas.html.io.HtmlDoc;
import net.bodz.bas.html.io.HtmlOutputFormat;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.mod.table.HtmlForModTable;
import net.bodz.bas.html.io.mod.table.ModTable;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlP;
import net.bodz.bas.html.io.tag.HtmlTable;
import net.bodz.bas.html.io.tag.HtmlTd;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.io.tag.HtmlUl;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.io.ITreeOut;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.potato.element.IPropertyAccessor;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclFilters;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.site.vhost.VhostDataContexts;
import net.bodz.bas.std.rfc.mime.ContentType;
import net.bodz.bas.std.rfc.mime.ContentTypes;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.CoObjectMask;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.SectionDiv_htm1;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.repr.QuickIndexFormat;
import com.bee32.zebra.tk.sea.TableMetadata;
import com.bee32.zebra.tk.sea.TableMetadataRegistry;
import com.bee32.zebra.tk.site.FormatFn;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;
import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.site.ZpCmdsToolbar;
import com.bee32.zebra.tk.stat.MonthTrends;
import com.bee32.zebra.tk.stat.ValueDistrib;
import com.bee32.zebra.tk.stat.impl.MonthTrendsMapper;
import com.bee32.zebra.tk.stat.impl.ValueDistribMapper;
import com.bee32.zebra.tk.util.Counters;
import com.bee32.zebra.tk.util.ModTables;

public abstract class SlimIndex_htm<X extends QuickIndex<T>, T extends CoObject, M>
        extends AbstractHtmlViewBuilder<X>
        implements IZebraSiteAnchors, IZebraSiteLayout, IArtifactConsts, IFontAwesomeCharAliases {

    protected IFormDecl formDecl;
    protected PathFieldMap indexFields;

    public SlimIndex_htm(Class<?> valueClass) {
        super(valueClass);
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
    public void precompile(IHtmlViewContext ctx, IUiRef<X> ref) {
        super.precompile(ctx, ref);
        IHtmlHeadData metaData = ctx.getHeadData();
        // metaData.addDependency("datatables.bootstrap.js", SCRIPT);
        // metaData.addDependency("datatables.responsive.js", SCRIPT);
        metaData.addDependency("all-data", PSEUDO);
        // metaData.addDependency("datatables.tableTools.js", SCRIPT);
    }

    @Override
    public final IHtmlOut buildHtmlViewStart(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
        X index = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(index).getRemainingPath() == null;
        if (arrivedHere && index.defaultPage && fn.redirect.addSlash(ctx))
            return null;

        SwitcherModelGroup switchers = new SwitcherModelGroup();
        CoObjectMask mask = buildSwitchers(ctx, switchers);
        ctx.getRequest().setAttribute(mask.getClass().getName(), mask);

        if (index.format == QuickIndexFormat.JSON) {
            HttpServletResponse response = ctx.getResponse();
            PrintWriter writer = response.getWriter();
            buildJson(ctx, writer, ref);
            return ctx.stop();
        }

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);

        IHtmlOut dataOut = out;

        if (layout.isShowFrame()) {
            HtmlDiv body1 = out.div().id(ID.body1).class_("col-xs-9 col-sm-9 col-lg-10");
            {
                HtmlDiv headDiv = body1.div().id(ID.head).class_("zu-info clearfix");
                buildHead(ctx, headDiv, ref, arrivedHere);

                dataOut = body1;
            }

        } // showFramework

        beforeData(ctx, dataOut, ref);

        if (arrivedHere)
            new FilterSectionDiv(switchers).build(dataOut, "s-filter");

        if (arrivedHere) {
            boolean extradata = false;
            // dataOut =
            List<? extends CoObject> list = dataIndex(ctx, dataOut, ref);

            afterData(ctx, dataOut, ref);

            if (extradata)
                dumpFullData(dataOut, list);

            if (layout.isShowFrame())
                sections(ctx, dataOut, ref);
        }

        userData(ctx, dataOut, ref);

        if (arrivedHere) {
            List<String> extraScripts = ctx.getVariable(VAR.extraScripts);
            extraScripts.add(getClass().getSimpleName() + ".js");
        }

        return dataOut;
    }

    @Override
    public void buildHtmlViewEnd(IHtmlViewContext ctx, IHtmlOut out, IHtmlOut body, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
        X index = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(index).getRemainingPath() == null;

        if (out == null)
            throw new NullPointerException("out");

        HtmlDiv rightCol = out.div().id(ID.right_col).class_("col-xs-3 col-sm-3 col-lg-2");
        if (arrivedHere) {
            HtmlDiv previewDiv = rightCol.div().id(ID.preview).align("center");
            // previewDiv.div().class_("icon fa").text(FA_COFFEE);
            previewDiv.img().src(_webApp_.join("zebra/scene1.png").absoluteHref());

            HtmlDiv infosel = rightCol.div().id(ID.infosel);

            HtmlTable _table1 = infosel.table().width("100%").class_("zu-layout");
            HtmlTr _tr1 = _table1.tr();
            HtmlTd _td1 = _tr1.td();
            _td1.h2().text("选中的信息");
            HtmlTd _td2 = _tr1.td().align("right").style("width: 3.5em");

            _td2.a().id("zp-infosel-edit").href("").target("_blank").text("[编辑]");

            infosel.div().id(ID.infoselData);

            HtmlDiv refdocsDiv = rightCol.div().id(ID.infoman);
            refdocsDiv.h2().text("管理文献");

            ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(getValueType());
            HtmlUl infomanUl = refdocsDiv.ul().id(ID.infoman_ul);
            if (arrivedHere) {
                List<String> seeList = classDoc.getTag("see", List.class);
                if (seeList != null)
                    for (String see : seeList)
                        infomanUl.li().verbatim(see);
            }
        }
    }

    protected abstract CoObjectMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException;

    protected void buildHead(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref, boolean indexPage) {
        X manager = ref.get();
        Class<?> objectType = manager.getObjectType();

        DataContext dataContext = VhostDataContexts.getInstance().forCurrentRequest();

        Class<IMapperTemplate<?, M>> mapperClass = IMapper.fn.requireMapperClass(objectType);
        IMapperTemplate<?, M> mapper = dataContext.getMapper(mapperClass);

        ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(getValueType());

        HtmlDiv titleDiv = out.div().id(ID.title);
        {
            Object label = classDoc.getTag("label");
            titleDiv.h1().verbatim(Nullables.toString(label));

            iString docText = classDoc.getText();
            HtmlP subTitle = titleDiv.p().class_("sub");
            subTitle.verbatim(Nullables.toString(docText.getHeadPar()));
        }

        HtmlDiv headCol1 = out.div().id(ID.headCol1).class_("col-xs-6");
        HtmlDiv statDiv = headCol1.div().id(ID.stat);
        {
            Class<M> maskClass = (Class<M>) CoObjectMask.findMaskClass(objectType);
            if (maskClass == null)
                throw new IllegalUsageException("No mask for " + objectType);
            M mask = ctx.query(maskClass);

            Map<String, Number> countMap = mapper.count(mask);
            HtmlUl statUl = statDiv.ul();
            for (String key : countMap.keySet()) {
                String name = Counters.displayName(key);
                long count = countMap.get(key).longValue();
                statUl.li().text(name + " " + count + " 条");
            }
        }

        HtmlDiv cmdsDiv = headCol1.div().id(ZpCmdsToolbar.ID);
        {
            cmdsDiv.div().id(ZpCmds0Toolbar.ID);
            cmdsDiv.div().id(ZpCmds1Toolbar.ID);
        }

        out.div().id(ID.headCol2).class_("col-xs-6");
    }

    protected void beforeData(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
    }

    protected abstract List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref)
            throws ViewBuilderException, IOException;

    protected void afterData(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
    }

    protected void sections(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
        Class<?> entityType = ref.get().getObjectType();
        TableMetadataRegistry registry = TableMetadataRegistry.getInstance();
        TableMetadata metadata = registry.get(entityType);
        String table = metadata.getName();

        IHtmlOut sect;
        sect = new SectionDiv_htm1("记录/Records", IFontAwesomeCharAliases.FA_BAR_CHART).build(out, "s-records");
        {
            IHtmlOut chart = sect.div().id("monthcreation").class_("plot");
            chart.p().text("最近一段时期的数据使用情况：");
            chart.div().class_("view").style("height: 15em");
            HtmlDiv dataDiv = chart.div().class_("data");
            MonthTrendsMapper monthTrendsMapper = ctx.query(MonthTrendsMapper.class);
            StringBuffer sb = new StringBuffer(4096);
            for (MonthTrends row : monthTrendsMapper.all(table, "creation")) {
                sb.append(row.getYear());
                sb.append("-");
                sb.append(row.getMonth());
                sb.append("\t");
                sb.append(row.getCount());
                sb.append(";");
            }
            dataDiv.text(sb);

            sect.hr();

            chart = sect.div().id("userdist").class_("plot");
            chart.p().text("贡献最多的用户是他们：");
            chart.div().class_("view").style("height: 300px");
            dataDiv = chart.div().class_("data");
            ValueDistribMapper valueDistribMapper = ctx.query(ValueDistribMapper.class);
            sb.setLength(0);
            for (ValueDistrib row : valueDistribMapper.userDistrib(table)) {
                sb.append(row.getValueLabel());
                sb.append("\t");
                sb.append(row.getCount());
                sb.append(";");
            }
            dataDiv.text(sb);
        }
    }

    protected void userData(IHtmlViewContext ctx, IHtmlOut out, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
    }

    protected void buildJson(IHtmlViewContext ctx, PrintWriter out, IUiRef<X> ref)
            throws ViewBuilderException, IOException {
        HtmlDoc doc = new HtmlDoc(ITreeOut.NULL, HtmlOutputFormat.DEFAULT);
        HtmlForModTable html = new HtmlForModTable(doc);
        dataIndex(ctx, html, ref);

        JSONWriter jw = new JSONWriter(out);
        for (ModTable table : html.getTables())
            ModTables.toJson(table, jw);
    }

    protected void dumpFullData(IHtmlOut parent, List<? extends CoObject> dataset) {
        int count = 0;
        Collection<FieldDeclGroup> groups = formDecl
                .getFieldGroups(FieldDeclFilters.maxDetailLevel(DetailLevel.DETAIL));
        for (CoObject entity : dataset) {
            if (count++ > 3)
                break;
            HtmlDiv dtab = parent.div().id("data-" + entity.getId());
            for (FieldDeclGroup group : groups) {
                if (group.isEmpty())
                    continue;

                FieldCategory category = group.getCategory();
                String catLabel = category == FieldCategory.NULL ? "基本信息" : IXjdocElement.fn.labelName(category);
                HtmlDiv groupDiv = dtab.div().class_("zu-fgroup").style("line-heignt: 2em");
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

                    HtmlDiv line = groupDiv.div();
                    line.span().class_("zu-flabel zu-w50").text(IXjdocElement.fn.labelName(fieldDecl) + ": ");
                    line.span().text(value);
                } // for field
            } // for field-group
        } // for entity
    }

    protected List<T> postfilt(List<T> list) {
        return list;
    }

    protected <tag_t extends IHtmlOut> tag_t ref(tag_t tag, CoObject e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

    protected final FormatFn fmt = new FormatFn();

    protected static class fn2 {

        public static String labels(Collection<? extends CoObject> elements) {
            if (elements == null)
                return null;
            StringBuilder sb = new StringBuilder(elements.size() * 80);
            for (CoObject o : elements) {
                if (sb.length() != 0)
                    sb.append(", ");
                sb.append(o.getLabel());
            }
            return sb.toString();
        }

    }

}
