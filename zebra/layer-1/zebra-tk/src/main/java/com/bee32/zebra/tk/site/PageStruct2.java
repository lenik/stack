package com.bee32.zebra.tk.site;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.IHtmlViewContext;

import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;

public class PageStruct2 {

    IHtmlViewContext ctx;

    public PageStruct2(IHtmlViewContext ctx) {
        this.ctx = ctx;
    }

    public IHtmlTag getTitle() {
        return ctx.getTag(ID.title);
    }

    public IHtmlTag getMainCol() {
        return ctx.getTag(ID.main_col);
    }

    public IHtmlTag getStat() {
        return ctx.getTag(ID.stat);
    }

    public IHtmlTag getCmds0() {
        return ctx.getTag(ID.cmds0);
    }

    public IHtmlTag getCmds1() {
        return ctx.getTag(ID.cmds1);
    }

    public IHtmlTag getLinksUl() {
        return ctx.getTag(ID.links_ul);
    }

    public IHtmlTag getInfomanUl() {
        return ctx.getTag(ID.infoman_ul);
    }

    public IHtmlTag getExtradata() {
        return ctx.getTag(ID.extradata);
    }

}
