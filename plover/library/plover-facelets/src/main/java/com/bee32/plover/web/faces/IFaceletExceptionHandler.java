package com.bee32.plover.web.faces;

/**
 * You should declare &#64;RequestMapping on the handler type.
 */
public interface IFaceletExceptionHandler {

    ExceptionHandleResult handle(FaceletExceptionContext context, Throwable exception);

}
