package com.bee32.plover.faces;

import com.bee32.plover.arch.util.IPriority;

public interface IFacesExceptionTranslator
        extends IPriority {

    int SKIP = 0; // Can't handle it.
    int FILTER = 1; // Handle partial.
    int REPLACE = 2; // Handled.

    int handle(Throwable exception);

    String translate(Throwable exception, String message);

}
