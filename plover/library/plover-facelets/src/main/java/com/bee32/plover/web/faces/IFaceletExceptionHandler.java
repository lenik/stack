package com.bee32.plover.web.faces;

import com.bee32.plover.arch.util.IPriority;

/**
 * You should declare &#64;RequestMapping on the handler type.
 */
public interface IFaceletExceptionHandler
        extends IPriority {

    boolean isFullStackSearch();

    ExceptionHandleResult handle(FaceletExceptionContext context, Throwable exception);

}
