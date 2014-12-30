package com.bee32.zebra.tk.site;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlPTag;
import net.bodz.bas.html.dom.tag.HtmlUlTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.tinylily.model.base.CoEntity;

public abstract class Zc3Template_CE<T extends CoEntity>
        extends AbstractHtmlViewBuilder<T>
        implements IZebraSiteAnchors, IZebraSiteLayout {

    protected IFormDecl formStruct;
    protected List<IFieldDecl> indexFields;

    public Zc3Template_CE(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<T> ref, IOptions options)
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
        }

        HtmlDivTag rightCol = body1.div().id(ID.right_col).class_("hidden-xs col-sm-3 col-lg-2 info");
        {
            HtmlDivTag previewDiv = rightCol.div().id(ID.preview).align("center");
            previewDiv.div().class_("icon fa").text(IFontAwesomeCharAliases.FA_COFFEE);

            HtmlDivTag infosel = rightCol.div().id(ID.infosel);
        }

        return ctx;
    }

    protected void titleInfo(PageStruct p) {
        Class<?> entType = getValueType();
        ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(entType);

        p.title.h1().text(classDoc.getTag("label"));

        iString docText = classDoc.getText();
        HtmlPTag subTitle = p.title.p().class_("sub");
        subTitle.verbatim(docText.getHeadPar());

        HtmlUlTag statUl = p.stat.ul();
        statUl.li().text("总计 3155 种");
        statUl.li().text("有效 3014 种");
        statUl.li().text("在用 541 种");

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

}
