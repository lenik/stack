package com.bee32.plover.web.faces;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

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

            ExceptionQueuedEventContext context = event.getContext();

            Throwable exception = context.getException();
            Class<? extends Throwable> exceptionType = exception.getClass();

            IFaceletExceptionHandler handler = handlerMap.get(exceptionType);

            if (handler == null)
                continue;

            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FaceletExceptionContext fec = new FaceletExceptionContext(facesContext);

                String resultView = handler.handle(fec, exception);
                System.err.println("resultView -> " + resultView);

                ExternalContext extContext = facesContext.getExternalContext();
                String url = extContext.encodeActionURL(extContext.getRequestContextPath() + "/login.jsf");

                extContext.redirect(url);

            } catch (IOException e) {
                throw new FacesException(e.getMessage(), e);
            } finally {
                iter.remove();
            }
        }
        super.handle();
    }

    static final Map<Class<?>, IFaceletExceptionHandler> handlerMap;
    static {
        handlerMap = new HashMap<Class<?>, IFaceletExceptionHandler>();
    }

    public static void register(Class<? extends Throwable> exceptionType, IFaceletExceptionHandler handler) {
        if (exceptionType == null)
            throw new NullPointerException("exceptionType");
        if (handler == null)
            throw new NullPointerException("handler");
        handlerMap.put(exceptionType, handler);
    }

    public static void unregister(Class<? extends Throwable> exceptionType) {
        if (exceptionType == null)
            throw new NullPointerException("exceptionType");
        handlerMap.remove(exceptionType);
    }

    public static void unregister(IFaceletExceptionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        Iterator<Entry<Class<?>, IFaceletExceptionHandler>> iter = handlerMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Class<?>, IFaceletExceptionHandler> entry = iter.next();
            IFaceletExceptionHandler _handler = entry.getValue();
            if (_handler == handler)
                iter.remove();
        }
    }

}
