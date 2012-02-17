package com.bee32.plover.faces.el;

import javax.el.ELException;
import javax.free.IdentifiedException;

import com.bee32.plover.arch.exception.ExceptionHandleResult;
import com.bee32.plover.arch.exception.ForException;
import com.bee32.plover.faces.FaceletExceptionContext;
import com.bee32.plover.faces.FaceletsExceptionHandler;
import com.bee32.plover.faces.utils.FacesUILogger;

/**
 * 将 EL 求解器错误输出到 p:growl 之类的 message 渲染器上。
 */
@ForException(ELException.class)
public class ELErrorMessageFeh
        extends FaceletsExceptionHandler {

    @Override
    public ExceptionHandleResult handle(Throwable exception, FaceletExceptionContext context) {
        exception.printStackTrace();
        Throwable elCause = exception.getCause();
        if (elCause instanceof IllegalArgumentException) {
            // elCause.getStackTrace()...
            getUILogger();
        } else if (elCause instanceof IdentifiedException) {
            FacesUILogger logger = getUILogger();
            logger.error("页面逻辑错误", exception);
        }
        return handled();
    }

}
