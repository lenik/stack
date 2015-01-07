package com.bee32.zebra.tk.htm;

import java.io.IOException;

import net.bodz.bas.c.string.StringQuote;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.artifact.IArtifactDependency;
import net.bodz.bas.html.dom.tag.HtmlHeadTag;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlHeadData;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;

public abstract class RespTemplate<T>
        extends AbstractHtmlViewBuilder<T>
        implements IZebraSiteAnchors, IZebraSiteLayout, IArtifactConsts {

    public RespTemplate(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public void preview(IHtmlViewContext ctx, IUiRef<T> ref, IOptions options) {
        super.preview(ctx, ref, options);

        IHtmlHeadData metaData = ctx.getHeadData();
        metaData.setMeta(IHtmlHeadData.META_AUTHOR, "谢继雷 (Xiè Jìléi)");
        metaData.setMeta(IHtmlHeadData.META_VIEWPORT, //
                "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0");

        metaData.addDependency("jquery-min", SCRIPT).setPriority(IArtifactDependency.HIGH);
        metaData.addDependency("jquery-ui-min", SCRIPT).setPriority(IArtifactDependency.HIGH);
        metaData.addDependency("bootstrap3.js", SCRIPT).setPriority(IArtifactDependency.HIGH);
        metaData.addDependency("font-awesome", STYLESHEET).setPriority(IArtifactDependency.LOW);
        metaData.addDependency("icheck.js", SCRIPT);
        metaData.addDependency("magnific-popup.js", SCRIPT);
        metaData.addDependency("parsley.js", SCRIPT);
    }

    protected void respHead(IHtmlViewContext ctx, HtmlHeadTag head)
            throws IOException {
        writeHeadMetas(ctx, head);
        writeHeadImports(ctx, head);

        // stylesheets
        head.link().css(_jQueryUIThemes_ + "black-tie/jquery.ui.all.css");
        head.link().css(_webApp_ + "site.css");
        // head.link().css(_webApp_ + "s-yellow.css");
        head.link().css(_webApp_ + "print.css").media("print");

        // scripts
        head.script().javascript("" + //
                "var _webApp_ = " + StringQuote.qq(_webApp_) + ";\n" + //
                "var _js_ = " + StringQuote.qq(_js_) + ";\n" + //
                "");
    }

}
