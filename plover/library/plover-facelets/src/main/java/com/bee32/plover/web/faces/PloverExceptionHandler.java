package com.bee32.plover.web.faces;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.free.IllegalUsageException;
import javax.free.TypeHierMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Class<? extends Throwable> exceptionType = exception.getClass();

            Set<IFaceletExceptionHandler> handlers = handlerMap.floor(exceptionType);

            if (handlers == null || handlers.isEmpty())
                continue;

            for (IFaceletExceptionHandler handler : handlers) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FaceletExceptionContext fec = new FaceletExceptionContext(facesContext);

                ExceptionHandleResult result = handler.handle(fec, exception);
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

    static final TypeHierMap<Set<IFaceletExceptionHandler>> handlerMap;
    static {
        handlerMap = new TypeHierMap<Set<IFaceletExceptionHandler>>();
    }

    public static void register(Class<? extends Throwable> exceptionType, IFaceletExceptionHandler handler) {
        if (exceptionType == null)
            throw new NullPointerException("exceptionType");
        if (handler == null)
            throw new NullPointerException("handler");
        Set<IFaceletExceptionHandler> handlers = handlerMap.get(exceptionType);
        if (handlers == null) {
            handlers = new HashSet<IFaceletExceptionHandler>();
            handlerMap.put(exceptionType, handlers);
        }
        handlers.add(handler);
    }

    public static void unregister(IFaceletExceptionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        Iterator<Entry<Class<?>, Set<IFaceletExceptionHandler>>> iter = handlerMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Class<?>, Set<IFaceletExceptionHandler>> entry = iter.next();
            Set<IFaceletExceptionHandler> handlers = entry.getValue();
            if (handlers != null)
                handlers.remove(handlers);
            // if (handlers.isEmpty()) iter.remove();
        }
    }

}
