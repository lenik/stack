package com.bee32.zebra.oa.site;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.c.type.IndexedTypes;
import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.html.meta.HtmlViewBuilder;
import net.bodz.bas.log.Logger;
import net.bodz.bas.log.LoggerFactory;
import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.IPathDispatchable;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.site.org.ICrawlable;
import net.bodz.bas.site.org.ICrawler;
import net.bodz.bas.std.rfc.http.CacheControlMode;
import net.bodz.bas.std.rfc.http.ICacheControl;

import com.bee32.zebra.oa.login.LoginForm;
import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.model.base.CoObjectController;
import com.tinylily.model.base.CoObjectIndex;
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
        setQueryContext(VhostDataService.getInstance());

        for (Class<? extends CoObjectIndex> indexClass : IndexedTypes.list(CoObjectIndex.class, false)) {
            Class<?> objectType = indexClass.getAnnotation(ObjectType.class).value();

            List<String> paths = new ArrayList<>();
            paths.add(Strings.hyphenatize(objectType.getSimpleName()));

            PathToken aPathToken = objectType.getAnnotation(PathToken.class);
            if (aPathToken != null)
                paths.add(StringArray.join("/", aPathToken.value()));

            TableName aTableName = objectType.getAnnotation(TableName.class);
            if (aTableName != null)
                paths.add(aTableName.value());

            CoObjectController controller = new CoObjectController(getQueryContext(), objectType, indexClass);
            for (String path : paths)
                pathMap.put(path, controller);
        }
    }

    /** ⇱ Implementation Of {@link ICacheControl}. */
    /* _____________________________ */static section.iface __CACHE__;

    @Override
    public int getMaxAge() {
        return 0;
    }

    @Override
    public CacheControlMode getCacheControlMode() {
        return CacheControlMode.NO_CACHE;
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
        String token = tokens.peek();
        switch (token) {
        case "login":
            return PathArrival.shift(previous, new LoginForm(), tokens);
        }

        return super.dispatch(previous, tokens);
    }

    /** ⇱ Implementation Of {@link ICrawlable}. */
    /* _____________________________ */static section.iface __CRAWL__;

    @Override
    public void crawlableIntrospect(ICrawler crawler) {
    }

}
