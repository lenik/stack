package com.bee32.zebra.tk.site;

import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.IHtmlTag;

import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;

public class PageStruct {

    public IHtmlTag title;
    public IHtmlTag mainCol;
    public IHtmlTag stat;
    public IHtmlTag cmds0;
    public IHtmlTag cmds1;
    public IHtmlTag linksUl;
    public IHtmlTag infomanUl;
    public IHtmlTag extradata;

    public PageStruct(IHtmlViewContext ctx) {
        title = ctx.getTag(ID.title);
        mainCol = ctx.getTag(ID.main_col);
        stat = ctx.getTag(ID.stat);
        cmds0 = ctx.getTag(ID.cmds0);
        cmds1 = ctx.getTag(ID.cmds1);
        linksUl = ctx.getTag(ID.links_ul);
        infomanUl = ctx.getTag(ID.infoman_ul);
        extradata = ctx.getTag(ID.extradata);
    }

}