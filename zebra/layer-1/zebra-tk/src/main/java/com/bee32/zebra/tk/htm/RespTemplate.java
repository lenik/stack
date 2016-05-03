package com.bee32.zebra.tk.htm;

import java.io.IOException;

import net.bodz.bas.c.string.StringQuote;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.dom.IHtmlHeadData;
import net.bodz.bas.html.io.tag.HtmlHead;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.site.artifact.LibFontsArtifacts;
import net.bodz.bas.site.artifact.LibJsArtifacts;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;

/**
 * 1. RespTemplate <|--- OaSite_htm <|--- ErpSite_htm. 2. ErpSite -> QuickIndex*, SlimIndex_htm 3.
 * CoObject*, SlimForm_htm
 */
public abstract class RespTemplate<T>
        extends AbstractHtmlViewBuilder<T>
        implements IZebraSiteAnchors, IZebraSiteLayout, IArtifactConsts {

    public RespTemplate(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public void precompile(IHtmlViewContext ctx, IUiRef<T> ref) {
        super.precompile(ctx, ref);

        IHtmlHeadData metaData = ctx.getHeadData();
        metaData.setMeta(IHtmlHeadData.META_AUTHOR, "谢继雷 (Xiè Jìléi)");
        metaData.setMeta(IHtmlHeadData.META_VIEWPORT, //
                "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0");

        metaData.addDependency(LibJsArtifacts.jQuery);
        metaData.addDependency(LibJsArtifacts.jQueryUi);
        metaData.addDependency(LibJsArtifacts.bootstrap3_js);
        metaData.addDependency(LibFontsArtifacts.fontAwesome);
        metaData.addDependency(LibJsArtifacts.allInputs);
        metaData.addDependency(LibJsArtifacts.allEffects);
    }

    protected void respHead(IHtmlViewContext ctx, HtmlHead head)
            throws IOException {
        fn.head.writeHeadData(ctx, head);

        // stylesheets
        // String theme = "cupertino";
        String theme = "sunny";
        head.link().css(_jQueryUIThemes_ + theme + "/jquery.ui.all.css");
        head.link().css(_webApp_ + "site.css");
        head.link().css(_webApp_ + "s-yellow.css");
        head.link().css(_webApp_ + "print.css").media("print");

        // scripts
        head.script().javascriptSrc(_webApp_ + "bower_components/flot.curvedlines/curvedLines.js");

        head.script().javascriptSrc(_webApp_ + "js1/fixup.js");
        head.script().javascript("" + //
                "var _webApp_ = " + StringQuote.qq(_webApp_) + ";\n" + //
                "var _js_ = " + StringQuote.qq(_js_) + ";\n" + //
                "");
    }

}
