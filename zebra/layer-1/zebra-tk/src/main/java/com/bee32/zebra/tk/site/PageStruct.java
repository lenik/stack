package com.bee32.zebra.tk.site;

import net.bodz.bas.html.dom.HtmlDoc;
import net.bodz.bas.html.dom.IHtmlTag;

import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;

public class PageStruct {

    public IHtmlTag title;
    public IHtmlTag body1;
    public IHtmlTag stat;
    public IHtmlTag cmds0;
    public IHtmlTag cmds1;
    public IHtmlTag infomanUl;
    public IHtmlTag extradata;
    public IHtmlTag scripts;

    public PageStruct(HtmlDoc doc) {
        title = doc.getElementById(ID.title);
        body1 = doc.getElementById(ID.body1);
        stat = doc.getElementById(ID.stat);
        cmds0 = doc.getElementById(ID.cmds0);
        cmds1 = doc.getElementById(ID.cmds1);
        infomanUl = doc.getElementById(ID.infoman_ul);
        extradata = doc.getElementById(ID.extradata);
        scripts = doc.getElementById(ID.scripts);
    }

}
