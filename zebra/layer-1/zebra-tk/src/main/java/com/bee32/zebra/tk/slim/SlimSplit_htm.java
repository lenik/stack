package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlP;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;
import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.site.ZpCmdsToolbar;

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
    public final IHtmlOut buildHtmlViewStart(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException {
        T value = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(value).getRemainingPath() == null;
        if (arrivedHere && addSlash(ctx))
            return null;

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (layout.isShowFrame()) {
            {
                HtmlDiv headDiv = out.div().id(ID.head).class_("zu-info clearfix");
                buildHead(ctx, headDiv, ref);
            }

            // IHtmlTag rightCol = doc.getElementById(ID.right_col);
        } // showFramework

        if (arrivedHere) {
            List<String> extraScripts = ctx.getVariable(VAR.extraScripts);
            extraScripts.add(getClass().getSimpleName() + ".js");
        }

        buildBody(ctx, out, ref);

        return out;
    }

    protected void buildHead(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref) {
        ClassDoc classDoc = Xjdocs.getDefaultProvider().getOrCreateClassDoc(getValueType());
        HtmlDiv titleDiv = out.div().id(ID.title);

        Object label = classDoc.getTag("label");
        titleDiv.h1().verbatim(Nullables.toString(label));

        iString docText = classDoc.getText();
        HtmlP subTitle = titleDiv.p().class_("sub");
        subTitle.verbatim(Nullables.toString(docText.getHeadPar()));

        HtmlDiv headCol1 = out.div().id(ID.headCol1).class_("col-xs-6");
        headCol1.div().id(ID.stat);

        HtmlDiv cmdsDiv = headCol1.div().id(ZpCmdsToolbar.ID);
        cmdsDiv.div().id(ZpCmds0Toolbar.ID);
        cmdsDiv.div().id(ZpCmds1Toolbar.ID);

        out.div().id(ID.headCol2).class_("col-xs-6");
    }

    protected abstract void buildBody(IHtmlViewContext ctx, IHtmlOut out, IUiRef<T> ref)
            throws ViewBuilderException, IOException;

}
