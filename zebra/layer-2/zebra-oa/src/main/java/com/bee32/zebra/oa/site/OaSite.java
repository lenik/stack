package com.bee32.zebra.oa.site;

import net.bodz.bas.html.HtmlViewBuilder;
import net.bodz.bas.log.Logger;
import net.bodz.bas.log.LoggerFactory;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.IPathDispatchable;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.site.BasicSite;
import net.bodz.bas.site.org.ICrawlable;
import net.bodz.bas.site.org.ICrawler;

/**
 * @label OA Site Frame
 */
@HtmlViewBuilder(OaSiteVbo.class)
public class OaSite
        extends BasicSite {

    static final Logger logger = LoggerFactory.getLogger(OaSite.class);

    public String googleId;
    public String baiduId;

    public OaSite() {
    }

    @Override
    public String getSiteUrl() {
        return "http://zebra.bee32.com/oa";
    }

    /** ⇱ Implementation Of {@link IPathDispatchable}. */
    /* _____________________________ */static section.iface __PATH_DISP__;

    @Override
    public synchronized IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        // String token = tokens.peek();
        return super.dispatch(previous, tokens);
    }

    /** ⇱ Implementation Of {@link ICrawlable}. */
    /* _____________________________ */static section.iface __CRAWL__;

    @Override
    public void crawlableIntrospect(ICrawler crawler) {
    }

}
