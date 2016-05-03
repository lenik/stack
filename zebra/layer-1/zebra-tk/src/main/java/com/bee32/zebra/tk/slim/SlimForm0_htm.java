package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.StringPart;
import net.bodz.bas.c.type.TypeKind;
import net.bodz.bas.err.IllegalConfigException;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.err.NotImplementedException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlA;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlInput;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.util.AbstractForm_htm;
import net.bodz.bas.http.viz.IHttpViewBuilder;
import net.bodz.bas.http.viz.IHttpViewBuilderFactory;
import net.bodz.bas.i18n.dom1.ElementComparator;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IProperty;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.potato.ref.UiPropertyRef;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.meta.Face;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.site.config.PathConventions;
import net.bodz.bas.t.variant.IVarMapSerializable;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.ParameterMapVariantMap;
import net.bodz.bas.typer.Typers;
import net.bodz.bas.typer.std.IParser;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.entity.IId;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.CoObject;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.hbin.PickDialog;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout.VAR;

public abstract class SlimForm0_htm<T>
        extends AbstractForm_htm<T>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public SlimForm0_htm(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public void precompile(IHtmlViewContext ctx, IUiRef<T> ref) {
        super.precompile(ctx, ref);

        PageLayout pageLayout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        HttpServletRequest request = ctx.getRequest();
        String view = request.getParameter("view:");
        if ("form".equals(view))
            pageLayout.setShowFrame(false);
    }

    @Override
    public IHtmlOut buildHtmlViewStart(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException {
        if (fn.redirect.addSlash(ctx, ref))
            return null;
        return super.buildHtmlViewStart(ctx, out, ref);
    }

    @Override
    protected void nullInstance(IHtmlOut out, IUiRef<T> ref) {
        out.text("null@" + ref.getValueType());
    }

    @Override
    protected IHtmlOut beginCategory(IHtmlViewContext ctx, IHtmlOut out, FieldCategory category)
            throws ViewBuilderException, IOException {
        String catName = category == FieldCategory.NULL ? "null" : category.getName();
        out = out.fieldset().class_("zu-fcat").id("zp-fcat-" + catName);

        // IHtmlTag head = out.h2().id("zp-fcat-" + catName);
        IHtmlOut head = out.legend();

        head.span().class_("icon fa").text(FA_CUBE);
        if (category == FieldCategory.NULL) {
            head.text("基本信息");
        } else {
            head.text(IXjdocElement.fn.labelName(category));
        }
        return out;
    }

    @Override
    protected List<IFieldDecl> overrideFieldSelection(IUiRef<?> instanceRef, FieldDeclGroup group,
            List<IFieldDecl> selection)
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

        Collections.sort(selection, ElementComparator.LOCALE);
        return selection;
    }

    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected IHtmlOut beginField(IHtmlViewContext ctx, IHtmlOut out, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
        HtmlDiv div = out.div().class_("zu-field");
        div.attr("f", fieldDecl.getName());

        IHtmlOut labelDiv = div.label().class_("zu-flabel");
        String labelName = IXjdocElement.fn.labelName(fieldDecl);
        labelDiv.text(labelName + ":");

        // IHtmlTag valueDiv = labelDiv.div().class_("zu-fvalue");
        IHtmlOut valueDiv = div.span().class_("zu-fvalue");
        return valueDiv;
    }

    @Override
    protected void fieldBody(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> instanceRef, IFieldDecl fieldDecl)
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

            HtmlInput id_hidden = null;
            if (!fieldDecl.isReadOnly()) {
                id_hidden = out.input().type("hidden").name(inputName + ".id");
                // FieldHtmlUtil.apply(id_hidden, fieldDecl);
            }

            HtmlInput label_text = out.input().type("text").class_("noprint").name(inputName + ".label");
            label_text.placeholder(fieldDecl.getPlaceholder());
            label_text.readonly("readonly");
            label_text.attr("ec", typeName);

            if (current != null) {
                if (id_hidden != null)
                    id_hidden.value(current.getId());
                label_text.value(current);

                out.span().class_("print").text(current);
            }

            if (!fieldDecl.isReadOnly()) {
                HtmlA pickerLink = out.a().class_("zu-pickcmd noprint");
                String pathName = PathConventions.getPathToken(clazz);
                pickerLink.attr("data-url", _webApp_ + pathName + "/picker.html");
                pickerLink.attr("data-title", "选择" + fieldDecl.getLabel() + "...");
                pickerLink.text("选择");
            } else {
                label_text.disabled("disabled");
            }

            if (!description.isEmpty())
                description = IXjdocElement.fn.labelName(type) + "：" + description;
        } // CoObject

        else {
            IHttpViewBuilderFactory factory = ctx.query(IHttpViewBuilderFactory.class);
            if (factory == null)
                throw new IllegalConfigException(IHttpViewBuilderFactory.class + " isn't set.");

            IHttpViewBuilder<Object> viewBuilder;
            Face aFace = property.getAnnotation(Face.class);
            String[] features = {};
            if (aFace != null)
                features = aFace.value();

            viewBuilder = factory.getViewBuilder(clazz, features);
            if (viewBuilder == null) {
                String msg = String.format("No view builder for %s (features: %s).", clazz, Arrays.asList(features));
                throw new NotImplementedException(msg);
            }

            // String htmName = viewBuilder.getClass().getSimpleName();
            // out.comment("Foo-Field: " + typeName + " -- " + htmName);
            viewBuilder.buildViewStart(ctx, out, propertyRef);
        }

        if (!description.isEmpty()) {
            out.a().class_("fa icon helpdoc-switcher").text(FA_QUESTION_CIRCLE);
            out.div().class_("helpdoc").verbatim(description);
        }
    }

    @Override
    protected void endField(IHtmlViewContext ctx, IHtmlOut out, IHtmlOut fieldOut, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected void endCategory(IHtmlViewContext ctx, IHtmlOut out, IHtmlOut catOut, FieldCategory category) {
    }

    @Override
    protected IHtmlOut extras(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException {
        PickDialog.build(out, "picker1");

        List<String> extraScripts = ctx.getVariable(VAR.extraScripts);
        extraScripts.add("impl/" + getClass().getSimpleName() + ".js");

        return out;
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

        if (obj instanceof IVarMapSerializable) {
            IVarMapSerializable p = (IVarMapSerializable) obj;
            p.readObject(parameterMap);
        }
    }

    void injectValue(Object obj, IFieldDecl fieldDecl, IVariantMap<String> parameterMap)
            throws ParseException, ReflectiveOperationException {
        String name = fieldDecl.getName();
        Class<?> type = fieldDecl.getValueType();

        String[] strv = parameterMap.getStringArray(name);
        if (strv == null)
            return;
        String str = StringArray.join("\n", strv);

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

    protected <tag_t extends IHtmlOut> tag_t ref(tag_t tag, CoObject e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

}
