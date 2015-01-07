package com.bee32.zebra.tk.site;

import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;

public class DataViewAnchors<T> {

    public IHtmlTag frame;
    public IHtmlTag data;
    public IHtmlTag extradata;

    public boolean dataList;
    List<T> list;

    public boolean noList() {
        return data == null && extradata == null;
    }

    public boolean dataList() {
        return dataList;
    }

}
