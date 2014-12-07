package com.bee32.zebra.oa.site;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.c.type.IndexedTypes;
import net.bodz.bas.html.HtmlViewBuilder;
import net.bodz.bas.log.Logger;
import net.bodz.bas.log.LoggerFactory;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.IPathDispatchable;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.site.org.ICrawlable;
import net.bodz.bas.site.org.ICrawler;

import com.tinylily.repr.CoEntityManager;
import com.tinylily.site.LilyStartSite;

/**
 * @label OA Site Frame
 */
@HtmlViewBuilder(OaSiteVbo.class)
public class OaSite
        extends LilyStartSite {

    static final Logger logger = LoggerFactory.getLogger(OaSite.class);

    public String googleId;
    public String baiduId;

    public OaSite() {
        for (Class<? extends CoEntityManager> clazz : IndexedTypes.list(CoEntityManager.class, false)) {
            CoEntityManager instance;
            try {
                instance = clazz.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            String base = clazz.getSimpleName();
            if (base.endsWith("Manager"))
                base = base.substring(0, base.length() - 7);
            String name = Strings.hyphenatize(base);
            pathMap.put(name, instance);

            PathToken aPathToken = clazz.getAnnotation(PathToken.class);
            if (aPathToken != null) {
                String path = StringArray.join("/", aPathToken.value());
                pathMap.put(path, instance);
            }
        }
    }

    /**
     * TODO getSiteUrl...
     */
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
