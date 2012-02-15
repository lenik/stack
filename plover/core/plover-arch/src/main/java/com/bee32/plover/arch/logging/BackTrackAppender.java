package com.bee32.plover.arch.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

public class BackTrackAppender
        extends ConsoleAppender {

    EltManager eltManager = EltManager.getInstance();

    @Override
    protected void subAppend(LoggingEvent event) {
        super.subAppend(event);
        ThrowableInformation ti = event.getThrowableInformation();
        if (ti != null)
            eltManager.addException(event.getMessage(), ti);
    }

}
