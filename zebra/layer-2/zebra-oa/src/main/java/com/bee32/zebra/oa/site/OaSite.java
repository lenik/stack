package com.bee32.zebra.oa.site;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.type.IndexedTypes;
import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.http.ctx.CurrentHttpService;
import net.bodz.bas.log.Logger;
import net.bodz.bas.log.LoggerFactory;
import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.IPathDispatchable;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.site.config.PathConventions;
import net.bodz.bas.site.file.UploadHandler;
import net.bodz.bas.site.org.ICrawlable;
import net.bodz.bas.site.org.ICrawler;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.site.vhost.VhostDataContexts;
import net.bodz.bas.site.vhost.VirtualHostScope;
import net.bodz.bas.std.rfc.http.CacheControlMode;
import net.bodz.bas.std.rfc.http.ICacheControl;
import net.bodz.lily.model.base.CoObjectIndex;
import net.bodz.lily.site.LilyStartSite;

import com.bee32.zebra.oa.calendar.LogCalendar;
import com.bee32.zebra.oa.console.Console;
import com.bee32.zebra.oa.etc.HelpIndex;
import com.bee32.zebra.oa.etc.ServiceIndex;
import com.bee32.zebra.oa.etc.SiteUtilities;
import com.bee32.zebra.oa.login.LoginForm;
import com.bee32.zebra.tk.repr.QuickController;

/**
 * @label OA Site Frame
 */
@VirtualHostScope
public class OaSite
        extends LilyStartSite {

    static final Logger logger = LoggerFactory.getLogger(OaSite.class);

    IVirtualHost vhost;
    DataContext dataContext;

    String googleId;
    String baiduId;

    public OaSite(IVirtualHost vhost) {
        this.vhost = vhost;
        this.dataContext = VhostDataContexts.getInstance().get(vhost);
        setQueryContext(dataContext);

        for (Class<?> _indexClass : IndexedTypes.list(CoObjectIndex.class, false)) {
            @SuppressWarnings("unchecked")
            Class<? extends CoObjectIndex<?>> indexClass = (Class<? extends CoObjectIndex<?>>) _indexClass;

            Class<?> objectType = indexClass.getAnnotation(ObjectType.class).value();
            QuickController controller = new QuickController(dataContext, objectType, indexClass);

            for (String token : PathConventions.getPathTokens(objectType))
                pathMap.put(token, controller);
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
        Object target = null;

        String token = tokens.peek();
        if (token == null)
            return null;

        switch (token) {
        case "login":
            target = new LoginForm();
            break;

        case "calendar":
            LogCalendar logCalendar = new LogCalendar();
            logCalendar.setDataContext(dataContext);
            target = logCalendar;
            break;

        case "console":
            target = new Console();
            break;

        case "help":
            target = new HelpIndex();
            break;

        case "service":
            target = new ServiceIndex();
            break;

        case "util":
            target = new SiteUtilities();
            break;

        case "upload":
            UploadHandler uploadHandler = new UploadHandler();
            HttpServletRequest request = CurrentHttpService.getRequest();
            try {
                target = uploadHandler.handlePostRequest(request);
            } catch (IOException e) {
                throw new PathDispatchException(e.getMessage(), e);
            }
            break;
        }

        if (target != null)
            return PathArrival.shift(previous, target, tokens);
        else
            return super.dispatch(previous, tokens);
    }

    /** ⇱ Implementation Of {@link ICrawlable}. */
    /* _____________________________ */static section.iface __CRAWL__;

    @Override
    public void crawlableIntrospect(ICrawler crawler) {
    }

}
