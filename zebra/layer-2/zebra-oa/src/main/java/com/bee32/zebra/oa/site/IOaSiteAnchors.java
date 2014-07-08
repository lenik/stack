package com.bee32.zebra.oa.site;

import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.site.IBasicSiteAnchors;

public interface IOaSiteAnchors
        extends IBasicSiteAnchors {

    IAnchor _img_ = _webApp_.join("img/");

}
