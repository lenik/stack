package com.bee32.plover.util;

import java.util.Set;

public interface IMultiFormat {

    String toString(FormatStyle format);

    void toString(PrettyPrintStream out, FormatStyle format);

    void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, final int depth);

}
