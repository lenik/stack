package com.bee32.plover.arch.exception;

import com.bee32.plover.arch.util.IPriority;

public interface IExceptionHandler
        extends IPriority {

    boolean isFullStackSearch();

    ExceptionHandleResult handle(Throwable exception, Object context);

}
