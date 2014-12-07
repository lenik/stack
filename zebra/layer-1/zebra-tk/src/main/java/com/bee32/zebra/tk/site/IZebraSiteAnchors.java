package com.bee32.zebra.tk.site;

import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.site.IBasicSiteAnchors;

public interface IZebraSiteAnchors
        extends IBasicSiteAnchors {

    IAnchor _img_ = _webApp_.join("img/");

}
