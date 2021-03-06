package com.bee32.zebra.tk.slim;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.type.TypeChain;
import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.bas.db.ibatis.IMapperProvider;
import net.bodz.bas.err.Err;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlButton;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlForm;
import net.bodz.bas.html.io.tag.HtmlInput;
import net.bodz.bas.html.io.tag.HtmlUl;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.io.Stdio;
import net.bodz.bas.io.impl.TreeOutImpl;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.req.IMethodOfRequest;
import net.bodz.bas.repr.req.MethodNames;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.entity.IId;
import net.bodz.lily.entity.Instantiables;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.sql.FooMapper;

public abstract class SlimForm_htm<T extends CoObject>
        extends SlimForm0_htm<T> {

    static String DEFAULT_NAV = "reload";

    public SlimForm_htm(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    protected IHtmlOut beforeForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException {
        out = super.beforeForm(ctx, out, ref);

        PageLayout pageLayout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (pageLayout.isShowFrame()) {
            setUpFrame(ctx, out, ref);
        }

        process(ctx, out, ref);
        return out;
    }

    protected void process(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException {
        HttpServletRequest req = ctx.getRequest();
        IMethodOfRequest methodOfRequest = ctx.query(IMethodOfRequest.class);
        String methodName = methodOfRequest.getMethodName();

        IMapperProvider provider = ctx.query(IMapperProvider.class);
        Class<FooMapper<T, ?>> mapperClass = IMapper.fn.getMapperClass(ref.getValueType());
        FooMapper<T, ?> mapper = provider.getMapper(mapperClass);

        T obj = ref.get();
        Number id = (Number) obj.getId();

        try {
            // data.populate(req.getParameterMap());
            inject(ref, req.getParameterMap());
        } catch (Exception e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        switch (methodName) {
        case MethodNames.CREATE:
            if (tryPersist(true, ctx, out, ref)) {
                id = (Number) obj.getId();
                String navHint = req.getParameter("-nav");
                if (navHint == null)
                    navHint = DEFAULT_NAV;
                switch (navHint) {
                case "create-more":
                    try {
                        T skel = Instantiables._instantiate(ref.getValueType());
                        ref.set(skel);
                    } catch (ReflectiveOperationException e) {
                        throw new ViewBuilderException(e.getMessage(), e);
                    }
                    break;
                case "reload":
                    obj = mapper.select(id.longValue());
                    ref.set(obj);
                    break;
                }
            }
            break;

        case MethodNames.UPDATE:
            if (tryPersist(false, ctx, out, ref)) {
                // reload from database.
                try {
                    if (id == null)
                        throw new NullPointerException("id");
                    T reload = mapper.select(id.longValue());
                    ref.set(reload);
                } catch (ClassCastException e) {
                    TypeChain.dumpTypeTree(mapper.getClass(), TreeOutImpl.from(Stdio.cerr));
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

    protected void setUpFrame(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected HtmlForm beginForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> ref)
            throws ViewBuilderException, IOException {
        HtmlForm form = out.form();
        form.id("form1").method("post");
        buildFormHead(ctx, form.div().class_("form-head"), ref);
        form.div();

        CoObject entity = (CoObject) ref.get();
        Number id = (Number) entity.getId();
        boolean creation = id == null || id.intValue() == 0;
        HtmlInput methodParam = form.input().type("hidden").name("m:");
        if (creation)
            methodParam.value(MethodNames.CREATE);
        else
            methodParam.value(MethodNames.UPDATE);

        return form;
    }

    protected void buildFormHead(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> ref) {
    }

    @Override
    protected boolean overrideFieldGroup(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> instanceRef, FieldDeclGroup group)
            throws ViewBuilderException, IOException {
        String typeName = instanceRef.getValueType().getSimpleName();

        String simpleName = group.getCategory().getTagClass().getSimpleName();
        switch (simpleName) {
        case "Object":
            return buildBasicGroup(ctx, out, instanceRef, group);
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

    protected boolean buildBasicGroup(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> instanceRef, FieldDeclGroup group)
            throws ViewBuilderException, IOException {
        return false;
    }

    @Override
    protected void endForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> ref)
            throws ViewBuilderException, IOException {
        // out.hr();
        HtmlDiv div = out.div();
        HtmlButton submitButton = div.button().type("submit");
        submitButton.span().class_("fa icon").text(FA_ANGLE_DOUBLE_UP);
        submitButton.text("??????????????????");

        HtmlButton resetButton = div.button();
        resetButton.type("button");
        resetButton.onclick("javascript: form1.reset()");
        resetButton.title("????????????????????????????????????????????????");
        resetButton.i().class_("fa icon").text(FA_ERASER);
        resetButton.text("??????");
    }

    protected boolean tryPersist(boolean create, IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref) {
        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        HttpServletRequest req = ctx.getRequest();
        String navHint = req.getParameter("-nav");
        if (navHint == null)
            navHint = DEFAULT_NAV;
        String promptSuccess = req.getParameter("prompt.success");
        if (promptSuccess == null)
            promptSuccess = "????????????";

        try {
            Object id = persist(create, ctx, out, ref);
            IId<Object> obj = (IId<Object>) ref.get();
            obj.setId(id);

            HtmlDiv alert = out.div().class_("alert alert-success");
            alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
            alert.iText(FA_CHECK_CIRCLE, "fa");
            alert.bText("[??????]").text(promptSuccess);
            alert.hr();
            HtmlDiv mesg = alert.div().class_("small");
            if (create) {
                mesg.text("????????????");
                HtmlUl ul = mesg.ul();

                switch (navHint) {
                case "create-more":
                    ul.li().text("?????????????????????????????????");
                    break;
                case "reload":
                    ul.li().text("??????????????????????????????");
                    break;
                }

                if (layout.isShowFrame()) {
                    switch (navHint) {
                    case "create-more":
                        ul.li().iText(FA_EXTERNAL_LINK_SQUARE, "fa")//
                                .aText("???????????????????????????", "../" + id + "/").text("?????????");
                        break;
                    case "reload":
                        ul.li().iText(FA_EXTERNAL_LINK_SQUARE, "fa")//
                                .aText("????????????????????????", "../new/").text("?????????");
                        break;
                    }

                    ul.li().iText(FA_TIMES_CIRCLE, "fa")//
                            .aText("???????????????", "javascript: window.close()").text("???");
                } else {
                    ul.li().text("??????????????????");
                }
            } else {
                mesg.text("?????????????????????????????????????????????" //
                        + (layout.isShowFrame() ? "????????????????????????????????????" : "????????????????????????"));
            }

            if (!layout.isShowFrame()) {
                out.script().javascript("iframeDone()");
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            e = Err.unwrap(e);
            HtmlDiv alert = out.div().class_("alert alert-danger");
            alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
            alert.span().class_("fa icon").text(FA_TIMES_CIRCLE);
            alert.strong().text("[??????]");
            alert.text("????????????: " + e.getMessage());
            alert.hr();
            errorDiag(alert.div().class_("small"), e);
            return false;
        }
    }

    protected Object persist(boolean create, IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws Exception {
        T data = ref.get();
        Object id = data.persist(ctx, out);
        return id;
    }

    protected void errorDiag(IHtmlOut out, Throwable e) {
        out.text("??????????????????????????????????????????????????????");
    }

}
