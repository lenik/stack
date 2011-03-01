package com.bee32.plover.view;

import com.bee32.plover.util.Mimes;

public abstract class HtmlRenderer
        extends ContentRenderer {

    public HtmlRenderer() {
        super(Mimes.text_html, Mimes.text_xhtml, Mimes.application_xhtml_xml);
    }

}
