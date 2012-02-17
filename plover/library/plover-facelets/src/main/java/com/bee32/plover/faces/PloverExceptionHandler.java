package com.bee32.plover.faces;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.exception.ExceptionHandleResult;
import com.bee32.plover.arch.exception.ExceptionHandlerManager;
import com.bee32.plover.arch.exception.IExceptionHandler;

public class PloverExceptionHandler
        extends ExceptionHandlerWrapper {

    static Logger logger = LoggerFactory.getLogger(PloverExceptionHandler.class);

    private final ExceptionHandler handler;

    public PloverExceptionHandler(ExceptionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        this.handler = handler;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return handler;
    }

    @Override
    public void handle()
            throws FacesException {
        Iterator<ExceptionQueuedEvent> iter = getUnhandledExceptionQueuedEvents().iterator();

        while (iter.hasNext()) {
            ExceptionQueuedEvent event = iter.next();
            logger.debug("PEH iterating over " + event);

            ExceptionQueuedEventContext eqeContext = event.getContext();
            Throwable exception = eqeContext.getException();

            for (Entry<IExceptionHandler, Throwable> entry : ExceptionHandlerManager.searchHandlers(exception).entrySet()) {
                IExceptionHandler handler = entry.getKey();
                Throwable exceptionInStack = entry.getValue();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                FaceletExceptionContext exceptionContext = new FaceletExceptionContext(facesContext);

                ExceptionHandleResult result = handler.handle(exceptionInStack, exceptionContext);
                switch (result.getType()) {
                case ExceptionHandleResult.TYPE_SKIP:
                    continue;

                case ExceptionHandleResult.TYPE_HANDLED:
                    iter.remove();
                    continue;

                case ExceptionHandleResult.TYPE_REDIRECT:
                    String resultView = (String) result.getValue();
                    if (resultView == null)
                        throw new NullPointerException("resultView");

                    ExternalContext extContext = facesContext.getExternalContext();

                    String href = extContext.getRequestContextPath() + resultView;
                    String url = extContext.encodeActionURL(href);

                    HttpServletResponse resp = (HttpServletResponse) extContext.getResponse();
                    boolean committed = resp.isCommitted();
                    if (committed)
                        throw new IllegalStateException("Already committed, can't redirect to " + url);

                    try {
                        extContext.redirect(url);
                    } catch (IOException e) {
                        throw new FacesException(e.getMessage(), e);
                    } finally {
                        iter.remove();
                    }
                    continue;

                default:
                    throw new IllegalUsageException("Bad handler result: " + result.getType());
                }
            }
        }
        super.handle();
    }

}
