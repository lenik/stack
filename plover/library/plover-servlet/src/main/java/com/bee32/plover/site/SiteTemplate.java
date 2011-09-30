package com.bee32.plover.site;

import com.bee32.plover.arch.util.TextMap;

public class SiteTemplate
        extends HtmlBuilder {

    static String SITE_CSS = "" //
            + "block {}" //
            + "banner {}" //
            + "menu {}" //
            + "footer {}";

    public SiteTemplate(TextMap args) {
        parse(args);
    }

    {
        html();
        head();
        {
            title("Site Manager").end();
            style().type("css").text(SITE_CSS).end();
            end();
        }

        body();
        {
            div().classAttr("");
        }

        endAll();

        form();
        table();
        endAll();
    }

    protected void sidebar() {

    }

    protected void content() {

    }

    protected void parse(TextMap args) {
    }

}
