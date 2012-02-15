package com.bee32.plover.arch.logging;

import java.util.Collection;

public interface IExceptionLogTargeter {

    Collection<? extends ExceptionLog> getLogTargets();

}
