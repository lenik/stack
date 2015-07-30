package com.bee32.zebra.tk.slim;

import java.io.IOException;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.dom.HtmlDoc;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlPTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;
import com.bee32.zebra.tk.site.PageStruct;

public abstract class SlimSplit_htm<T>
        extends AbstractHtmlViewBuilder<T>
        implements IZebraSiteAnchors, IZebraSiteLayout, IArtifactConsts, IFontAwesomeCharAliases {

    public SlimSplit_htm(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public boolean isFrame() {
        return true;
    }

    @Override
    public final IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        T value = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(value).getRemainingPath() == null;
        if (arrivedHere && enter(ctx))
            return null;

        HtmlDoc doc = ctx.getHtmlDoc();

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (layout.isShowFrame()) {
            IHtmlTag body1 = doc.getElementById(ID.body1);
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

            // IHtmlTag rightCol = doc.getElementById(ID.right_col);
        } // showFramework

        PageStruct page = new PageStruct(doc);

        if (layout.isShowFrame())
            titleInfo(ctx, ref, arrivedHere);

        if (arrivedHere) {
            new PageStruct(doc);
            page.scripts.script().javascriptSrc(getClass().getSimpleName() + ".js");
        }

        buildBody(ctx, out, ref, options);
        return out;
    }

    protected void titleInfo(IHtmlViewContext ctx, IUiRef<T> ref, boolean indexPage) {
        PageStruct p = new PageStruct(ctx.getHtmlDoc());
        ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(getValueType());
        Object label = classDoc.getTag("label");
        p.title.h1().verbatim(Nullables.toString(label));

        iString docText = classDoc.getText();
        HtmlPTag subTitle = p.title.p().class_("sub");
        subTitle.verbatim(Nullables.toString(docText.getHeadPar()));
    }

    protected abstract IHtmlTag buildBody(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException;

}
