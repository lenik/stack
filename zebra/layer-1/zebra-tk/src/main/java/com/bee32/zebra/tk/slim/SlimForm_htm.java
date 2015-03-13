package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.c.string.StringPart;
import net.bodz.bas.c.type.SingletonUtil;
import net.bodz.bas.c.type.TypeChain;
import net.bodz.bas.c.type.TypeKind;
import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.bas.db.ibatis.IMapperProvider;
import net.bodz.bas.db.meta.TableUtils;
import net.bodz.bas.err.Err;
import net.bodz.bas.err.IllegalConfigException;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.err.NotImplementedException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.AbstractHtmlTag;
import net.bodz.bas.html.dom.HtmlDoc;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.meta.HtmlViewBuilder;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewBuilder;
import net.bodz.bas.html.viz.IHttpViewBuilderFactory;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.html.viz.util.AbstractForm_htm;
import net.bodz.bas.io.Stdio;
import net.bodz.bas.io.impl.TreeOutImpl;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IProperty;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.potato.ref.UiPropertyRef;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclComparator;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.req.IMethodOfRequest;
import net.bodz.bas.repr.req.MethodNames;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.typer.Typers;
import net.bodz.bas.typer.std.IParser;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.hbin.PickDialog;
import com.bee32.zebra.tk.hbin.SplitForm;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.site.CoTypes;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.sql.FnMapper;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.PrevNext;
import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.IId;
import com.tinylily.model.base.IdType;
import com.tinylily.model.sea.ParameterMapVariantMap;

public abstract class SlimForm_htm<T extends CoObject>
        extends AbstractForm_htm<T>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public SlimForm_htm(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public void preview(IHttpViewContext ctx, IUiRef<T> ref, IOptions options) {
        super.preview(ctx, ref, options);

        PageLayout pageLayout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        HttpServletRequest request = ctx.getRequest();
        String view = request.getParameter("view:");
        if ("form".equals(view))
            pageLayout.hideFramework = true;
    }

    @Override
    public IHtmlTag buildHtmlView(IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return super.buildHtmlView(ctx, out, ref, options);
    }

    @Override
    protected void nullInstance(IHtmlTag out, IUiRef<T> ref) {
        out.text("null@" + ref.getValueType());
    }

    @Override
    protected IHtmlTag beforeForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {

        PageLayout pageLayout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (!pageLayout.hideFramework) {
            setUpFrame(ctx, out, ref, options);
        }
        process(ctx, out, ref, options);
        return out;
    }

    protected void process(IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        try {
            // data.populate(ctx.getRequest().getParameterMap());
            inject(ref, ctx.getRequest().getParameterMap());
        } catch (Exception e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        T obj = ref.get();
        Number id = (Number) obj.getId();

        IMethodOfRequest methodOfRequest = ctx.query(IMethodOfRequest.class);
        String methodName = methodOfRequest.getMethodName();
        switch (methodName) {
        case MethodNames.CREATE:
            if (tryPersist(true, ctx, out, ref))
                // success. create a new skel object.
                try {
                    T skel = ref.getValueType().newInstance();
                    ref.set(skel);
                } catch (ReflectiveOperationException e) {
                    throw new ViewBuilderException(e.getMessage(), e);
                }
            break;

        case MethodNames.UPDATE:
            if (tryPersist(false, ctx, out, ref)) {
                // reload from database.
                try {
                    FooMapper<T, ?> mapper = ctx.query(IMapperProvider.class).getMapperForObject(ref.getValueType());
                    T reload = mapper.select(id.longValue());
                    ref.set(reload);
                } catch (ClassCastException e) {
                    IMapper m = ctx.query(IMapperProvider.class).getMapperForObject(ref.getValueType());
                    TypeChain.dumpTypeTree(m.getClass(), TreeOutImpl.from(Stdio.cerr));
                    throw new ViewBuilderException(e);
                }
            }
            break;

        case MethodNames.READ:
        case MethodNames.EDIT:
        default:
            break;
        }
    }

    protected void setUpFrame(IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        HtmlDoc doc = ctx.getHtmlDoc();

        Class<?> type = ref.getValueType();
        T obj = ref.get();
        Number id = (Number) obj.getId();

        String tablename = TableUtils.tablename(type);
        FnMapper fnMapper = ctx.query(FnMapper.class);

        // FIXME consider access control and criteria.
        PrevNext prevNext = fnMapper.prevNext("public", tablename, //
                id == null ? Integer.MAX_VALUE : id.longValue());
        if (prevNext == null)
            prevNext = new PrevNext();

        IHtmlTag headCol2 = doc.getElementById(ID.headCol2);
        HtmlDivTag adjs = headCol2.div().class_("zu-links");
        adjs.div().text("操作附近的数据:");
        HtmlUlTag ul = adjs.ul();
        HtmlATag newLink = ul.li().a().href("../new/");
        newLink.iText(FA_FILE_O, "fa").text("新建");

        HtmlLiTag navs = ul.li();
        IHtmlTag prevLink = navs;
        if (prevNext.getPrev() != null)
            prevLink = prevLink.a().href("../" + prevNext.getPrev() + "/");
        prevLink.span().class_("fa icon").text(FA_CHEVRON_CIRCLE_LEFT);
        prevLink.text("前滚翻");

        IHtmlTag nextLink = navs;
        if (prevNext.getNext() != null)
            nextLink = nextLink.a().href("../" + prevNext.getNext() + "/");
        nextLink.text("后滚翻 ");
        nextLink.span().class_("fa icon").text(FA_CHEVRON_CIRCLE_RIGHT);
    }

    @Override
    protected HtmlFormTag beginForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> ref, IOptions options)
            throws ViewBuilderException, IOException {
        SplitForm form = new SplitForm(out);
        form.name("form").method("post");

        CoObject entity = (CoObject) ref.get();
        Number id = (Number) entity.getId();
        boolean creation = id == null || id.intValue() == 0;
        HtmlInputTag methodParam = form.input().type("hidden").name("m:");
        if (creation)
            methodParam.value(MethodNames.CREATE);
        else
            methodParam.value(MethodNames.UPDATE);

        return form;
    }

    @Override
    protected boolean overrideFieldGroup(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef,
            FieldDeclGroup group, IOptions options)
            throws ViewBuilderException, IOException {
        String typeName = instanceRef.getValueType().getSimpleName();

        String simpleName = group.getCategory().getTagClass().getSimpleName();
        switch (simpleName) {
        case "Object":
            return buildBasicGroup(ctx, out, instanceRef, group, options);
            // case "Content":
        case "Ranking":
        case "Metadata":
        case "Visual":
            return true;

        case "Version":
            switch (typeName) {
            case "Contact":
                return true;
            }
            break;

        case "Security":
            switch (typeName) {
            case "AccountingEntry":
            case "Contact":
            case "DeliveryItem":
            case "SalesOrderItem":
                return true;
            }
            break;
        }
        return false;
    }

    protected boolean buildBasicGroup(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException, IOException {
        return false;
    }

    @Override
    protected IHtmlTag beginCategory(IHttpViewContext ctx, IHtmlTag out, FieldCategory category)
            throws ViewBuilderException, IOException {
        String catName = category == FieldCategory.NULL ? "null" : category.getName();
        out = out.fieldset().class_("zu-fcat").id("zp-fcat-" + catName);

        // IHtmlTag head = out.h2().id("zp-fcat-" + catName);
        IHtmlTag head = out.legend();

        head.span().class_("icon fa").text(FA_CUBE);
        if (category == FieldCategory.NULL) {
            head.text("基本信息");
        } else {
            head.text(IXjdocElement.fn.labelName(category));
        }
        return out;
    }

    @Override
    protected List<IFieldDecl> overrideFieldSelection(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef,
            FieldDeclGroup group, List<IFieldDecl> selection, IOptions options)
            throws ViewBuilderException, IOException {

        Set<String> includes = new HashSet<String>();
        Set<String> excludes = new HashSet<String>();
        excludes.add("acl");
        selectFields(group, includes, excludes);
        if (includes.isEmpty())
            includes = null;
        if (excludes.isEmpty())
            excludes = null;

        if (includes != null || excludes != null) {
            Iterator<IFieldDecl> iterator = selection.iterator();
            while (iterator.hasNext()) {
                IFieldDecl field = iterator.next();
                boolean included = includes == null || includes.contains(field.getName());
                boolean excluded = excludes != null && excludes.contains(field.getName());
                if (!included || excluded)
                    iterator.remove();
            }
        }

        Collections.sort(selection, FieldDeclComparator.INSTANCE);
        return selection;
    }

    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected IHtmlTag beginField(IHttpViewContext ctx, IHtmlTag out, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
        HtmlDivTag div = out.div().class_("zu-field");
        div.attr("f", fieldDecl.getName());

        IHtmlTag labelDiv = div.label().class_("zu-flabel");
        String labelName = IXjdocElement.fn.labelName(fieldDecl);
        labelDiv.text(labelName + ":");

        // IHtmlTag valueDiv = labelDiv.div().class_("zu-fvalue");
        IHtmlTag valueDiv = div.span().class_("zu-fvalue");
        return valueDiv;
    }

    @Override
    protected void fieldBody(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, IFieldDecl fieldDecl,
            IOptions options)
            throws ViewBuilderException, IOException {
        IProperty property = fieldDecl.getProperty();
        UiPropertyRef<Object> propertyRef = new UiPropertyRef<Object>(instanceRef, property);
        Object value = propertyRef.get();

        Class<?> clazz = property.getPropertyType();
        IType type = PotatoTypes.getInstance().forClass(clazz);
        String typeName = clazz.getSimpleName();

        String description = Nullables.toString(fieldDecl.getHelpDoc());
        {
            if (description == null)
                description = "";

            String labelName = IXjdocElement.fn.labelName(fieldDecl);
            if (description.equals(labelName))
                description = "";

            // if (!fieldDecl.isEnabled()) description += "<p>Generated。";
        }

        // if (CoCode.class.isAssignableFrom(type)) {

        if (CoObject.class.isAssignableFrom(clazz)) {
            CoObject current = (CoObject) value;

            String inputName = fieldDecl.getInputName();
            if (inputName == null)
                inputName = fieldDecl.getName();

            HtmlInputTag id_hidden = null;
            if (!fieldDecl.isReadOnly()) {
                id_hidden = out.input().type("hidden").name(inputName + ".id");
                // FieldHtmlUtil.apply(id_hidden, fieldDecl, options);
            }

            HtmlInputTag label_text = out.input().type("text").name(inputName + ".label");
            label_text.placeholder(fieldDecl.getPlaceholder());
            label_text.readonly("readonly");
            label_text.attr("ec", typeName);

            if (current != null) {
                if (id_hidden != null)
                    id_hidden.value(current.getId());
                label_text.value(current);
            }

            if (!fieldDecl.isReadOnly()) {
                HtmlATag pickerLink = out.a().class_("zu-pickcmd");
                String pathToken = CoTypes.getPathToken(clazz);
                pickerLink.attr("data-url", _webApp_ + pathToken + "/picker.html");
                pickerLink.attr("data-title", "选择" + fieldDecl.getLabel() + "...");
                pickerLink.text("选择");
            } else {
                label_text.disabled("disabled");
            }

            if (!description.isEmpty())
                description = IXjdocElement.fn.labelName(type) + "：" + description;
        }

        else {
            IHttpViewBuilder<Object> viewBuilder;

            HtmlViewBuilder aViewBuilder = property.getAnnotation(HtmlViewBuilder.class);
            if (aViewBuilder != null && aViewBuilder.value().length > 0) {
                viewBuilder = (IHttpViewBuilder<Object>) SingletonUtil.instantiateCached(aViewBuilder.value()[0]);
            } else {
                IHttpViewBuilderFactory factory = ctx.query(IHttpViewBuilderFactory.class);
                if (factory == null)
                    throw new IllegalConfigException(IHttpViewBuilderFactory.class + " isn't set.");
                viewBuilder = factory.getViewBuilder(clazz);
            }

            // String htmName = viewBuilder.getClass().getSimpleName();
            // out.comment("Foo-Field: " + typeName + " -- " + htmName);
            viewBuilder.buildView(ctx, out, propertyRef, options);
        }

        if (!description.isEmpty()) {
            out.a().class_("fa icon helpdoc-switcher").text(FA_QUESTION_CIRCLE);
            out.div().class_("helpdoc").verbatim(description);
        }
    }

    @Override
    protected void endField(IHttpViewContext ctx, IHtmlTag out, IHtmlTag fieldOut, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected void endCategory(IHttpViewContext ctx, IHtmlTag out, IHtmlTag catOut, FieldCategory category) {
    }

    @Override
    protected void endForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> ref, IOptions options)
            throws ViewBuilderException, IOException {
        // out.hr();
        HtmlDivTag div = out.div();
        HtmlButtonTag submitButton = div.button().type("submit");
        submitButton.span().class_("fa icon").text(FA_ANGLE_DOUBLE_UP);
        submitButton.text("保存以上信息");

        HtmlButtonTag resetButton = div.button();
        resetButton.type("button");
        resetButton.onclick("javascript: form.reset()");
        resetButton.span().class_("fa icon").text(FA_ERASER);
        resetButton.text("复原").title("清除刚才输入的所有变更，重新写。");
    }

    @Override
    protected IHtmlTag extras(IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        new PickDialog(out, "picker1");

        PageStruct page = new PageStruct(ctx.getHtmlDoc());
        page.scripts.script().javascriptSrc("impl/" + getClass().getSimpleName() + ".js");

        return out;
    }

    protected boolean tryPersist(boolean create, IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref) {
        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);

        try {
            Object id = persist(create, ctx, out, ref);

            HtmlDivTag alert = out.div().class_("alert alert-success");
            alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
            alert.iText(FA_CHECK_CIRCLE, "fa");
            alert.bText("[成功]").text("保存成功");
            alert.hr();
            HtmlDivTag mesg = alert.div().class_("small");
            if (create) {
                mesg.text("您可以：");
                HtmlUlTag ul = mesg.ul();
                ul.li().text("继续创建新的记录，或者");
                if (layout.hideFramework) {
                    ul.li().text("关闭本窗口。");
                } else {
                    ul.li().iText(FA_EXTERNAL_LINK_SQUARE, "fa")//
                            .aText("返回刚才保存的记录", "../" + id + "/").text("，或者");
                    ul.li().iText(FA_TIMES_CIRCLE, "fa")//
                            .aText("关闭本窗口", "javascript: window.close()").text("。");
                }
            } else {
                mesg.text("您可以选择核对刚刚更新的记录，" //
                        + (layout.hideFramework ? "或者关闭本窗口。" : "或者翻页浏览临近的记录。"));
            }

            if (layout.hideFramework) {
                out.script().javascript("iframeDone()");
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            e = Err.unwrap(e);
            HtmlDivTag alert = out.div().class_("alert alert-danger");
            alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
            alert.span().class_("fa icon").text(FA_TIMES_CIRCLE);
            alert.strong().text("[错误]");
            alert.text("无法保存: " + e.getMessage());
            alert.hr();
            alert.div().class_("small").text("请检查您输入的数据，再重新保存一次。");
            return false;
        }
    }

    protected Object persist(boolean create, IHttpViewContext ctx, IHtmlTag out, IUiRef<T> ref)
            throws Exception {
        T data = ref.get();
        Object id = data.persist(ctx, out);
        return id;
    }

    protected final void inject(IUiRef<?> ref, Map<String, String[]> parameterMap)
            throws ParseException, ReflectiveOperationException {
        ParameterMapVariantMap variantMap = new ParameterMapVariantMap(parameterMap);
        inject(ref, variantMap);
    }

    protected void inject(IUiRef<?> ref, IVariantMap<String> parameterMap)
            throws ParseException, ReflectiveOperationException {
        Class<?> clazz = ref.getValueType();
        IFormDecl formDecl = IFormDecl.fn.forClass(clazz);

        Object obj = ref.get();

        for (IFieldDecl fieldDecl : formDecl.getFieldDecls()) {
            String name = fieldDecl.getName();
            Class<?> type = fieldDecl.getValueType();
            try {
                if (CoObject.class.isAssignableFrom(type))
                    injectCoRef(obj, fieldDecl, parameterMap);
                else
                    injectValue(obj, fieldDecl, parameterMap);
            } catch (Exception e) {
                throw new ParseException("property " + name + ": " + e.getMessage(), e);
            }
        } // for property
    }

    void injectValue(Object obj, IFieldDecl fieldDecl, IVariantMap<String> parameterMap)
            throws ParseException, ReflectiveOperationException {
        String name = fieldDecl.getName();
        Class<?> type = fieldDecl.getValueType();

        String str = parameterMap.getString(name);
        if (str == null)
            // if (parameterMap.containsKey(name))
            return;

        boolean isNull = false;
        switch (fieldDecl.getSpaceNormalization()) {
        case NONE:
            break;
        case RTRIM:
            str = StringPart.trimRight(str);
            break;
        case TRIM:
            str = str.trim();
            break;
        case XML_TEXT:
            str = str.trim();
            break;
        default:
        }

        switch (fieldDecl.getNullConvertion()) {
        case EMPTY:
            if (str.isEmpty())
                isNull = true;
            break;

        case NULL_TEXT:
            if ("null".equals(str))
                isNull = true;
            break;

        case ZERO:
            if ("0".equals(str))
                isNull = true;
            break;

        case NEGATIVE_ONE:
            if ("-1".equals(str))
                isNull = true;
            break;

        default:
            if (str.isEmpty()) {
                if (TypeKind.isNumeric(type))
                    isNull = true;
            }
        }

        IProperty property = fieldDecl.getProperty();
        Object value;
        if (isNull) {
            value = null;
        } else {
            IParser<?> parser = property.getTyper(IParser.class);
            if (parser == null)
                parser = Typers.getTyper(type, IParser.class);
            if (parser == null)
                throw new NotImplementedException("No parser for " + type);
            value = parser.parse(str);
        }
        property.setValue(obj, value);
    }

    void injectCoRef(Object obj, IFieldDecl fieldDecl, IVariantMap<String> parameterMap)
            throws ParseException, ReflectiveOperationException {
        Class<?> type = fieldDecl.getValueType();
        String name = fieldDecl.getName();
        String idStr = parameterMap.getString(name + ".id");
        if (idStr == null) // not specified: ignore
            return;

        CoObject skel = (CoObject) type.newInstance();

        if (!idStr.isEmpty()) {
            @SuppressWarnings("unchecked")
            IId<Object> skelw = (IId<Object>) skel;

            IdType aIdType = type.getAnnotation(IdType.class);
            if (aIdType == null)
                throw new IllegalUsageException("Unknown id type of " + type);
            Class<?> idType = aIdType.value();

            IParser<?> parser = Typers.getTyper(idType, IParser.class);
            Object id = parser.parse(idStr);

            skelw.setId(id);
        }

        String label = parameterMap.getString(name + ".label");
        skel.setLabel(label);

        IProperty property = fieldDecl.getProperty();
        property.setValue(obj, skel);
    }

    protected <tag_t extends AbstractHtmlTag<?>> tag_t ref(tag_t tag, CoObject e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

}
