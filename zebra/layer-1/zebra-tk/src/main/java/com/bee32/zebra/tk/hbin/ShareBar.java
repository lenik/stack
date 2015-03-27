package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;

public class ShareBar
        extends HtmlDivTag {

    public ShareBar(IHtmlTag parent) {
        super(parent, "div");
        align("center");

        HtmlDivTag div = this.div().class_("jiathis_style_32x32")
                .style("width: 500px; height: 32px");
        div.a().class_("jiathis_button_weixin");
        div.a().class_("jiathis_button_miliao");
        div.a().class_("jiathis_button_twitter");
        div.a().class_("jiathis_button_fb");
        div.a().class_("jiathis_button_qzone");
        div.a().class_("jiathis_button_tqq");
        div.a().class_("jiathis_button_delicious");
        div.a().class_("jiathis_button_pinterest");
        div.a().class_("jiathis_button_ydnote");
        div.a().class_("jiathis_button_kaixin001");
        div.a().class_("jiathis_button_cqq");
        // div.a().class_("jiathis jiathis_txt jiathis_separator jtico jtico_jiathis")
        // .href("_blank", "http://www.jiathis.com/share?uid=2016576");
        // div.a().class_("jiathis_counter_style");

        StringBuilder config = new StringBuilder();
        config.append("var jiathis_config={");
        config.append("    data_track_clickback: true,");
        config.append("    summary: \"\",");
        config.append("    shortUrl: false,");
        config.append("    hideMore: false");
        config.append("};");
        this.script().type("text/javascript").text(config.toString());

        this.script().type("text/javascript").src("http://v3.jiathis.com/code/jia.js?uid=2016576").charset("utf-8");

        StringBuilder css = new StringBuilder();
        css.append(".jiathis_style_32x32 .jtico { margin: 0 4px; }\n");
        css.append(".jiathis_style_32x32 a { border: none; padding: 0; }\n");
        this.style().type("text/css").verbatim(css.toString());
    }

}
