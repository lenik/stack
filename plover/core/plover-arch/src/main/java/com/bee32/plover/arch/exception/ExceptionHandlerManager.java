package com.bee32.plover.arch.exception;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IllegalUsageException;
import javax.free.TypeHierMap;

import com.bee32.plover.arch.util.PriorityComparator;

public class ExceptionHandlerManager {

    static final TypeHierMap<Set<IExceptionHandler>> handlerMap;
    static {
        handlerMap = new TypeHierMap<Set<IExceptionHandler>>();
    }

    public static void register(Class<? extends Throwable> exceptionType, IExceptionHandler handler) {
        if (exceptionType == null)
            throw new NullPointerException("exceptionType");
        if (handler == null)
            throw new NullPointerException("handler");
        Set<IExceptionHandler> handlers = handlerMap.get(exceptionType);
        if (handlers == null) {
            handlers = new HashSet<IExceptionHandler>();
            handlerMap.put(exceptionType, handlers);
        }
        handlers.add(handler);
    }

    public static void unregister(IExceptionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        Iterator<Entry<Class<?>, Set<IExceptionHandler>>> iter = handlerMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Class<?>, Set<IExceptionHandler>> entry = iter.next();
            Set<IExceptionHandler> handlers = entry.getValue();
            if (handlers != null)
                handlers.remove(handlers);
            // if (handlers.isEmpty()) iter.remove();
        }
    }

    // Scan FEHs.
    static {
        for (IExceptionHandler handler : ServiceLoader.load(IExceptionHandler.class)) {
            Class<?> fehClass = handler.getClass();

            ForException annotation = fehClass.getAnnotation(ForException.class);
            if (annotation == null)
                throw new IllegalUsageException(ForException.class + " is not declared on " + fehClass);

            Class<? extends Throwable> exceptionType = annotation.value();

            register(exceptionType, handler);
        }
    }

    public static Map<IExceptionHandler, Throwable> searchHandlers(Throwable exception) {

        Map<IExceptionHandler, Throwable> foundHandlers = new TreeMap<>(PriorityComparator.INSTANCE);

        boolean nested = false;
        while (exception != null) {
            Class<? extends Throwable> exceptionType = exception.getClass();

            Set<IExceptionHandler> handlers = handlerMap.floor(exceptionType);
            if (handlers != null) {
                for (IExceptionHandler handler : handlers) {
                    if (nested)
                        if (!handler.isFullStackSearch())
                            continue;
                    if (foundHandlers.containsKey(handler))
                        continue; // same handler should handle the out-most exception.
                    foundHandlers.put(handler, exception);
                }
            }

            exception = exception.getCause();
            nested = true;
        }
        return foundHandlers;
    }

}
