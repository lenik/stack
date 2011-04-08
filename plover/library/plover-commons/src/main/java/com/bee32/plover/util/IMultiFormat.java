package com.bee32.plover.util;

public interface IMultiFormat {

    String toString(FormatStyle format);

    void toString(PrettyPrintStream out, FormatStyle format);

}
