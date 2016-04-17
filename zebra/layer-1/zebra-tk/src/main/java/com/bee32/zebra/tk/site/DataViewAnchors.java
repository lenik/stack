package com.bee32.zebra.tk.site;

import java.util.List;

import net.bodz.bas.html.io.IHtmlOut;

public class DataViewAnchors<T> {

    public IHtmlOut frame;
    public IHtmlOut data;
    public IHtmlOut extradata;

    public boolean dataList;
    List<T> list;

    public boolean noList() {
        return data == null && extradata == null;
    }

    public boolean dataList() {
        return dataList;
    }

}
