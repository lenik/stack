package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlLabelTag;
import net.bodz.bas.html.dom.tag.HtmlTextareaTag;
import net.bodz.bas.html.util.FieldHtmlUtil;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.tinylily.model.mx.base.CoMessage;

public abstract class FooMesgVbo<T extends CoMessage<?>>
        extends FooVbo<T> {

    public FooMesgVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    protected boolean buildBasicGroup(IHtmlViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException {
        IFormDecl formDecl = group.getFormDecl();
        CoMessage<?> instance = (CoMessage<?>) instanceRef.get();

        SplitForm form = (SplitForm) out.getParent();
        out = form.head.table().class_("zu-msg");

        IHtmlTag subjectLine = out.tr().class_("msg-subject");
        {
            IFieldDecl subjectDecl = formDecl.getFieldDecl("subject");
            IHtmlTag subjectLabel = subjectLine.th().label();
            subjectLabel.span().class_("fa icon").text(FA_CHEVRON_DOWN);
            subjectLabel.text(subjectDecl.getLabel() + "：");

            HtmlInputTag subjectInput = subjectLine.td().input().name("subject").type("text");
            subjectInput.value(instance.getSubject());
            FieldHtmlUtil.apply(subjectInput, subjectDecl, options);
        }

        IHtmlTag textLine = out.tr().class_("msg-text");
        {
            IFieldDecl textDecl = formDecl.getFieldDecl("text");
            HtmlLabelTag textLabel = textLine.th().label();
            textLabel.span().class_("fa icon").text(FA_FILE_O);
            textLabel.text(textDecl.getLabel() + "：");

            HtmlTextareaTag textarea = textLine.td().textarea().name("text");
            String text = instance.getText();
            textarea.text(text == null ? "" : text);
            FieldHtmlUtil.apply(textarea, textDecl, options);
        }

        form.head.hr();
        return false;
    }

    @Override
    protected void selectBasicFields(Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("subject");
        excludes.add("text");
        excludes.add("textPreview");
        excludes.add("image");

        excludes.add("codeName");
        excludes.add("label");
        excludes.add("description");
    }

}
