package com.bee32.plover.arch.logging;

import java.util.Map;

import javax.free.Control;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.bee32.plover.arch.exception.ExceptionHandlerManager;
import com.bee32.plover.arch.exception.IExceptionHandler;

public class BackTrackAppender
        extends ConsoleAppender {

    EltManager eltManager = EltManager.getInstance();
    boolean skipIfHandlerDeclared = false;

    @Override
    protected void subAppend(LoggingEvent event) {
        super.subAppend(event);

        ThrowableInformation ti = event.getThrowableInformation();
        if (ti == null)
            return;

        Throwable exception = ti.getThrowable();
        if (isControl(exception))
            return;

        if (skipIfHandlerDeclared) {
            Map<IExceptionHandler, Throwable> handlers = ExceptionHandlerManager.searchHandlers(exception);
            if (!handlers.isEmpty())
                return;
        }

        eltManager.addException(event.getMessage(), exception, ti.getThrowableStrRep());
    }

    static boolean isControl(Throwable throwable) {
        boolean control = false;
        while (throwable != null) {
            if (throwable instanceof Control) {
                control = true;
                break;
            }
            throwable = throwable.getCause();
        }
        return control;
    }

}
