package com.bee32.plover.faces;

import com.bee32.plover.arch.util.IPriority;

public interface IErrorMessageTranslator
        extends IPriority {

    int SKIP = 0;
    int FILTER = 1;
    int REPLACE = 2;

    int handle(String message);

    String translate(String message);

}
